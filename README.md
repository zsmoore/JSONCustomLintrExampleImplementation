# JSONCustomLintrExampleImplementation
Implementation Repo for https://github.com/zsmoore/JSONCustomLintr  
Highlighting simplicity and gradle build integration
  
# File Structure  
The file structure for this repo is pretty simple.  
```
src/
    java.main/
        linting/
            rules/
               ArraySizeRule.java
               DisallowedStringRule.java
               JSONObjectKeyRule.java
            Register.java
            Runner.java 
```
In our rules folder we have a static method which will generate our `LintRule` to be used in the `Register.java` file.  

# Implementation Explanation

An example lint rule to prevent Array sizes from becoming too large is `ArraySizeRule.java` which looks like:    
```Java
/**
 * Prevent Arrays from being certain over size
 */
public class ArraySizeRule {

    private static final int MAX_ARRAY_SIZE = 3;
    private static final String ISSUE_ID = "Array Size Rule";

    public static LintRule getArraySizeRule() throws LintRule.Builder.LintRuleBuilderException {
        LintImplementation<JSONArray> maxArraySizeImplementation = new LintImplementation<JSONArray>() {
            @Override
            public Class<?> getClazz() {
                return JSONArray.class;
            }

            @Override
            public boolean shouldReport(JSONArray jsonArray) {
                return jsonArray.toList().size() > MAX_ARRAY_SIZE;
            }

            @Override
            public String report(JSONArray jsonArray) {
                return "JSONArray with contents " + jsonArray.toString() + " has more than " + MAX_ARRAY_SIZE + " objects";
            }
        };

        return new LintRule.Builder()
                .setImplementation(maxArraySizeImplementation)
                .setIssueId(ISSUE_ID)
                .setLevel(LintLevel.ERROR)
                .build();
    }
}
```
  
We simply make a `LintImplementation` and build it into our `LintRule`.  

After that we register all of our rules in `Register.java` as so:  

```Java
public class Register {

    public static LintRegister getLintRegister() {
        LintRegister lintRegister = new LintRegister();
        try {
            lintRegister.register(
                    ArraySizeRule.getArraySizeRule(),
                    DisallowedStringRule.getDissalowedStringRule(),
                    JSONObjectKeyRule.getJsonObjectKeyRule()
            );
        } catch (LintRule.Builder.LintRuleBuilderException e) {
            throw new RuntimeException("Error in rule creation.");
        }

        return lintRegister;
    }
}
```
  
We then use the `LintRegister` in the main `Runner.java` class.  

```Java
public class Runner {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }
        String filePath = args[0];
        ReportRunner reportRunner =
                new ReportRunner(new LintRunner(Register.getLintRegister(), filePath));
        reportRunner.report("build/reports");
    }
}
```
The `ReportRunner` class handles setting exit codes if we fail a lint check.

# Build Integration  
In this repo we implemented a gradle task to be able to be tied into any build integration we want to do with out project.  
  
  In the `build.gradle` we made a simple task to run our main method.  

  ``` gradle
  task lintJson(type: JavaExec) {
    classpath = sourceSets.main.runtimeClasspath
    main = 'main.linting.Runner'
    args './src/resources'
}
  ```
  Since our `ReportRunner` class handles exit codes automatically for us, we can simply tie this build task however we want into our pipeline and we will either fail or succeed based on our lint status.  

  # Tying into existing repos  
  When trying to hook up to existing repos we can take 2 approaches:  

  1. Make lint rules in an existing project that holds our json files (as seen here)  
  2. Make a separate library to hold our json lint rules, import into an existing project, and set up a build integration from there.  
    
# Report output  
![Report from running gradle task](https://www.zachary-moore.com/assets/pictures/exampleLint.png)