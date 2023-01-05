package ru.liner.facerapp.decoder;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathEvaluateProvider implements EvaluateProvider{
    @Override
    public String provide(String input) {

        try {
            Expression expression = new ExpressionBuilder(input)
                    .variables("pi", "e")
                    .functions(MathEvaluator.functionList)
                    .build()
                    .setVariable("pi", Math.PI)
                    .setVariable("e", Math.E);
            String result = MathEvaluator.numberFormat.format(expression.evaluate());
            if (result.endsWith(".0"))
                return result.substring(0, result.length() - 2);
            return result;
        } catch (Exception e) {
            return "0";
        }
    }
}
