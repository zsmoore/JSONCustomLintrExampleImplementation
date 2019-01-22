package main.linting;


import com.zachary_moore.lint.LintRegister;
import com.zachary_moore.lint.LintRule;
import main.linting.rules.ArraySizeRule;
import main.linting.rules.DisallowedStringRule;
import main.linting.rules.JSONObjectKeyRule;

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
