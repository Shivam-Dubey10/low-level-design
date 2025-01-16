public interface ArithematicExpression {
    public int evaluate();
}

public class Number implements ArithematicExpression {
    int number;
    public Number (int number) {
        this.number = number;
    }
    @override
    public int evaluate() {
        return number;
    }
}

public class Expression implements ArithematicExpression {
    Operation operation;
    ArithematicExpression leftExpression;
    ArithematicExpression rightExpression;
    public Expression(Operation operation, ArithematicExpression leftExpression, ArithematicExpression rightExpression) {
        this.operation = operation;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }
    @override
    public int evaluate () {
        int value = 0;
        switch (operation) {
            case Operation.ADD:
                value = leftExpression.evaluate() + rightExpression.evaluate();
                break;
            case Operation.SUBTRACT:
                value = leftExpression.evaluate() - rightExpression.evaluate();
                break;
            case Operation.MULTIPLY:
                value = leftExpression.evaluate() * rightExpression.evaluate();
                break;
            case Operation.DIVIDE:
                value = leftExpression.evaluate() / rightExpression.evaluate();
                break;
            default:
                break;
        }
        System.out.println("Expression evaluates to: " + value);
        return value;
    }
}

public enum Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE;
}

public class Main {
    public static void main (String[] args) {
        ArithematicExpression two = new Number(2);
        ArithematicExpression one = new Number(1);
        ArithematicExpression seven = new Number(7); //Thala for a reason
        ArithematicExpression add = new Expression(Operation.ADD, one, seven);
        ArithematicExpression multiply = new Expression(Operation.MULTIPLY, two, add);
        multiply.evaluate();
    }
}