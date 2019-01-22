package main.linting.rules;


import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.WrappedPrimitive;

public class DisallowedStringRule {

    private static String ISSUE_ID = "Dissalowed String Rule";
    private static String DISALLOWED_STRING = "test";

    public static LintRule getDissalowedStringRule() throws LintRule.Builder.LintRuleBuilderException {
        LintImplementation<WrappedPrimitive<String>> lintImplementation = new LintImplementation<WrappedPrimitive<String>>() {
            @Override
            public Class<?> getClazz() {
                return String.class;
            }

            @Override
            public boolean shouldReport(WrappedPrimitive<String> stringWrappedPrimitive) {
                return stringWrappedPrimitive.equals(DISALLOWED_STRING);
            }

            @Override
            public String report(WrappedPrimitive<String> wrappedPrimitive) {
                return "Found " + DISALLOWED_STRING + " with parent " + wrappedPrimitive.getParentObject().toString();
            }
        };

        return new LintRule.Builder()
                .setImplementation(lintImplementation)
                .setIssueId(ISSUE_ID)
                .setLevel(LintLevel.ERROR)
                .build();
    }
}
