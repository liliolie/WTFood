package com.example.wtfood.parser;


public class MyTokenizer extends Tokenizer {

    // This class is crating the token from user input. Create by Yen Kuo.

    private String buffer;
    private Token currentToken;


    public MyTokenizer(String text) {
        buffer = text;
        next();
    }

    @Override
    public void next() {
        // Replace all white space.
        buffer = buffer.replaceAll("\\s+", "");
        //buffer.toLowerCase();

        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }

        String current = "";
        if (buffer.charAt(0) != '<' && buffer.charAt(0) != '>' && buffer.charAt(0) != '=' && buffer.charAt(0) != ';') {
            current = getAttribute(buffer);
        } else if (buffer.charAt(0) == '<' || buffer.charAt(0) == '>' || buffer.charAt(0) == '=') {
            current = getComparator(buffer);
        } else {
            current = getEND(buffer);
        }

        // Lower case the inputs.

        current = current.toLowerCase();


        // Create new Token with the respectively value and attribute.
        if (current.equals("price")) {
            currentToken = new Token("price", Token.Attribute.PRICE);
        } else if (current.equals("rating")) {
            currentToken = new Token("rating", Token.Attribute.RATING);
        } else if (current.equals("=")) {
            currentToken = new Token("=", Token.Attribute.EQUAL);
        } else if (current.equals("<")) {
            currentToken = new Token("<", Token.Attribute.LESS);
        } else if (current.equals(">")) {
            currentToken = new Token(">", Token.Attribute.GREATER);
        } else if (current.equals(">=") || current.equals(("=>"))) {
            currentToken = new Token(">=", Token.Attribute.GOE);
        } else if (current.equals("<=") || current.equals("=<")) {
            currentToken = new Token("<=", Token.Attribute.LOE);
        } else if (Character.isDigit(current.charAt(0))) {
            currentToken = new Token(getValue(current), Token.Attribute.VALUE);
        } else if (current.equals(";")) {
            currentToken = new Token(";", Token.Attribute.END);
        } else if (current.equals("delivery")) {
            currentToken = new Token("delivery", Token.Attribute.DELIVERY);
        } else if (current.equals("y") || current.equals("n")) {

            currentToken = new Token(current, Token.Attribute.DELIVERYValue);
        } else {
            currentToken = new Token(current, Token.Attribute.UNKNOWN);
        }

        // Remove the char already been tokenized.
        buffer = buffer.substring(current.length());

    }

    /**
     * Get the string before operator or ;.
     *
     * @param currentBuffer String, The string from user and didn't be tokenized yet.
     */
    public String getAttribute(String currentBuffer) {
        int i = 0;
        while (currentBuffer.charAt(i) != '=' && currentBuffer.charAt(i) != '>' && currentBuffer.charAt(i) != '<' && currentBuffer.charAt(i) != ';') {
            i++;
            if (i == currentBuffer.length()) {
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);
    }

    /**
     * Get the string of operator.
     *
     * @param currentBuffer String, The string from user and didn't be tokenized yet.
     */
    public String getComparator(String currentBuffer) {
        int i = 0;
        while (currentBuffer.charAt(i) == '=' || currentBuffer.charAt(i) == '>' || currentBuffer.charAt(i) == '<') {
            i++;
            if (i == currentBuffer.length()) {
                return currentBuffer.substring(0, i);

            }
        }

        return currentBuffer.substring(0, i);
    }

    /**
     * Get the string before the number value.
     *
     * @param currentBuffer String, The string from user and didn't be tokenized yet.
     */
    public String getValue(String currentBuffer) {
        int i = 0;
        while (Character.isDigit(currentBuffer.charAt(0))) {
            i++;
            if (i == currentBuffer.length()) {
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);

    }

    /**
     * Get the string of ;.
     *
     * @param currentBuffer String, The string from user and didn't be tokenized yet.
     */
    public String getEND(String currentBuffer) {
        int i = 0;
        while (currentBuffer.charAt(i) == ';') {
            i++;
            if (i == currentBuffer.length()) {
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);
    }


    @Override
    public Token current() {
        return currentToken;
    }

    @Override
    public boolean hasNext() {
        return currentToken != null;
    }
}
