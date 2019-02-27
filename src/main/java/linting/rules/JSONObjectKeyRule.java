package linting.rules;

import com.zachary_moore.lint.LintImplementation;
import com.zachary_moore.lint.LintLevel;
import com.zachary_moore.lint.LintRule;
import com.zachary_moore.objects.JSONObject;

import java.util.Arrays;

/**
 * Warn about JsonObjects that have these keys
 */
public class JSONObjectKeyRule {

    private static final String[] DISALLOWED_KEYS = {"test", "x", "y", "phone"};
    private static final String ISSUE_ID = "JSON Object Key Rule";

    public static LintRule getJsonObjectKeyRule() throws LintRule.Builder.LintRuleBuilderException {
        LintImplementation<JSONObject> lintImplementation = new LintImplementation<JSONObject>() {
            @Override
            public Class<?> getClazz() {
                return JSONObject.class;
            }

            @Override
            public boolean shouldReport(JSONObject jsonObject) {
                if (jsonObject.toMap().containsKey(DISALLOWED_KEYS[0])
                        || jsonObject.toMap().containsKey(DISALLOWED_KEYS[1])
                        || jsonObject.toMap().containsKey(DISALLOWED_KEYS[2])
                        || jsonObject.toMap().containsKey(DISALLOWED_KEYS[3])) {
                    setReportMessage("JSONObject with content " + jsonObject.toString()
                            + " has keys "
                            + jsonObject.toMap().keySet()
                            + " disallowed keys are "
                            + Arrays.toString(DISALLOWED_KEYS));
                    return true;
                }
                return false;
            }
        };

        return new LintRule.Builder()
                .setImplementation(lintImplementation)
                .setLevel(LintLevel.WARNING)
                .setIssueId(ISSUE_ID)
                .build();
    }
}
