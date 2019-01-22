package main.linting;

import com.zachary_moore.runner.LintRunner;
import com.zachary_moore.runner.ReportRunner;

public class Runner {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }
        String filePath = args[0];
        ReportRunner reportRunner =
                new ReportRunner(new LintRunner(main.linting.Register.getLintRegister(), filePath));
        reportRunner.report("build/reports");
    }
}
