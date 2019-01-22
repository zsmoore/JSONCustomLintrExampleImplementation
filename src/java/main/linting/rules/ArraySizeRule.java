package main.linting.rules;


import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONArray;

/**
 * Prevent Arrays from being over size 5
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
