package sintactic.controllers;

import sintactic.models.Token;
import sintactic.libs.File;
import sintactic.libs.Regex;
import sintactic.schemas.TokenList;

import java.util.Hashtable;
import java.util.Map;

public class Lexico {

    private final String[] lines;
    private final Token[] array;
    private final Regex regex;
    private final Map<String, Integer> attributes;
    private final Map<String, String> tipoDato;
    private int count, index, line, attribute, flag;
    public TokenList tokens, errors;

    public Lexico() {
        File file = new File();
        String ruta = "app/src/main/java/sintactic/tools/code.txt";
        file.openFile(ruta);
        this.regex = new Regex();
        this.array = new Token[1000];
        this.lines = file.readFile();
        this.tokens = new TokenList();
        this.errors = new TokenList();
        this.count = 0;
        this.attribute = 500;
        this.line = 0;
        this.flag = 0;
        this.attributes = new Hashtable<>();
        this.tipoDato = new Hashtable<>();
    }

    public TokenList getErrors() {
        return errors;
    }

    public void analyze() {
        for (String linea : lines) {
            line++;
            automata(linea);
        }
    }

    public void printTokensTable() {
        System.out.println("---------------------------------TOKENS-------------------------------------------");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s%17s%25s%11s%10s%n", "LEXEMA", "TOKEN", "CLASIFICACION", "ATRIBUTO", "LINEA");
        System.out.println("-----------------------------------------------------------------------------------");
        tokens.printTokens();
        System.out.println("------------------------------------------------------------------------------------");
    }

    private int generateAttribute(String lexema) {
        int a = 0;
        switch (lexema) {
            case "Program" -> a = 401;
            case "Int" -> a = 402;
            case "Double" -> a = 403;
            case "Print" -> a = 404;
        }
        return a;
    }

    private int generateAttributeId(String lexema) {
        if (attributes.containsKey(lexema)) {
            attribute = attributes.get(lexema);
        } else {
            attributes.put(lexema, attribute);
        }
        attribute++;
        return attribute;
    }

    private String generateDataType(String lexema) {
        String tipo = "";
        if (tipoDato.containsKey(lexema)) {
            tipo = tipoDato.get(lexema);
        } else {
            if (flag == 1) {
                tipoDato.put(lexema, "Int");
                tipo = "Int";
            } else if (flag == 2) {
                tipoDato.put(lexema, "Double");
                tipo = "Double";
            }
        }
        return tipo;
    }

    private String generateDescription(String lexema) {
        String clasificacion;
        switch (lexema) {
            case "Program" -> clasificacion = "Iniciar Programa";
            case "Int" -> {
                clasificacion = "Tipo de Dato";
                flag = 1;
            }
            case "Double" -> {
                clasificacion = "Tipo de Dato";
                flag = 2;
            }
            case "Print" -> clasificacion = "Funcion";
            case "{" -> clasificacion = "LLave que abre";
            case "}" -> clasificacion = "LLave que cierra";
            case "+" -> clasificacion = "Operador Suma";
            case "-" -> clasificacion = "Operador Resta";
            case "(" -> clasificacion = "Parentesis que Abre";
            case ")" -> clasificacion = "Parentesis que Cierra";
            case ";" -> {
                clasificacion = "Punto y Coma";
                flag = 0;
            }
            case "=" -> clasificacion = "Asignacion";
            default -> clasificacion = generateDataType(lexema);

        }
        return clasificacion;
    }

    private Token generateToken(String lexema, String token, String descripcion,
            Object atributo, int linea) {
        Token toke;
        toke = new Token(lexema, token, descripcion, atributo, linea);
        this.array[index] = toke;
        index++;
        return toke;
    }

    private void automata(String linea) {
        int estado = 0;
        String lexema = "";
        for (int i = 0; i <= linea.length(); i++) {
            switch (estado) {
                case 0 -> {
                    if (i < linea.length() && linea.charAt(i) >= 'A' && linea.charAt(i) <= 'Z') {
                        lexema += linea.charAt(i);
                        estado = 1;
                    } else {
                        estado = 3;
                        i--;
                    }
                }
                case 1 -> {
                    if (i < linea.length()
                            && regex.esLETRA(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 2;
                        i--;
                    }
                }
                case 2 -> {
                    if (i < linea.length() && regex.esRESERVADA(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Reservada",
                                generateDescription(lexema),
                                generateAttribute(lexema), line);
                        tokens.add(temp);
                        estado = 0;
                        lexema = "";
                        i--;
                    } else {
                        estado = 14;
                        i--;
                    }
                }
                case 3 -> {
                    if (i < linea.length() && linea.charAt(i) >= 'a' && linea.charAt(i) <= 'z') {
                        lexema += linea.charAt(i);
                        estado = 4;
                    } else {
                        estado = 6;
                        i--;
                    }
                }
                case 4 -> {
                    if (i < linea.length()
                            && regex.esLETRA(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 16;
                        i--;
                    }
                }
                case 16 -> {
                    if (i < linea.length() && linea.charAt(i) >= '0' && linea.charAt(i) <= '9') {
                        lexema += linea.charAt(i);
                        estado = 17;
                    } else {
                        estado = 5;
                        i--;
                    }
                }
                case 17 -> {
                    if (i < linea.length()
                            && regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 5;
                        i--;
                    }
                }
                case 5 -> {
                    if (i < linea.length() && regex.esID(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Id",
                                generateDescription(lexema),
                                generateAttributeId(lexema),
                                line);
                        tokens.add(temp);
                        estado = 0;
                        lexema = "";
                        i--;
                    } else {
                        estado = 14;
                        i--;
                    }
                }
                case 6 -> {
                    if (i < linea.length() && linea.charAt(i) >= '0'
                            && linea.charAt(i) <= '9') {
                        lexema += linea.charAt(i);
                        estado = 7;
                    } else {
                        estado = 12;
                        i--;
                    }
                }
                case 7 -> {
                    if (i < linea.length()
                            && regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 8;
                        i--;
                    }
                }
                case 8 -> {
                    if (i < linea.length() && linea.charAt(i) == '.') {
                        lexema += linea.charAt(i);
                        estado = 9;
                    } else {
                        estado = 11;
                        i--;
                    }
                }
                case 9 -> {
                    if (i < linea.length() && linea.charAt(i) >= '0' && linea.charAt(i) <= '9') {
                        lexema += linea.charAt(i);
                        estado = 10;
                    } else {
                        lexema += linea.charAt(i);
                        estado = 14;
                    }
                }
                case 10 -> {
                    if (i < linea.length()
                            && regex.esDIGITO(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                    } else {
                        estado = 11;
                        i--;
                    }
                }
                case 11 -> {
                    if (regex.esENTERO(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Int",
                                "Int",
                                Integer.valueOf(lexema), line);
                        tokens.add(temp);
                    } else if (regex.esFLOTANTE(lexema)) {
                        Token temp;
                        temp = generateToken(lexema, "Double",
                                "Double",
                                Double.valueOf(lexema), line);
                        tokens.add(temp);
                    }
                    estado = 0;
                    lexema = "";
                    i--;
                }
                case 12 -> {
                    if (i < linea.length()
                            && regex.esOPERADOR(String.valueOf(
                                    linea.charAt(i)))) {
                        lexema += linea.charAt(i);
                        Token temp;
                        temp = generateToken(lexema, "Operador",
                                generateDescription(lexema),
                                (int) lexema.charAt(0), line);
                        tokens.add(temp);
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 13;
                        i--;
                    }
                }
                case 13 -> {
                    if (i < linea.length() && linea.charAt(i) == ' ') {
                        estado = 0;
                    } else {
                        estado = 15;
                        i--;
                    }
                }
                case 15 -> {
                    if (i < linea.length()
                            && regex.esDELIMITADOR(linea.charAt(i))) {
                        lexema += linea.charAt(i);
                        Token temp;
                        temp = generateToken(lexema, "Delimitador",
                                generateDescription(lexema),
                                (int) lexema.charAt(0), line);
                        tokens.add(temp);
                    }
                    estado = 0;
                    lexema = "";
                }
                case 14 -> {
                    Token temp;
                    temp = generateToken(lexema, "error",
                            " No pertenece a ninguna categoria lexica "
                                    + "en la linea " + line,
                            210, line);
                    errors.add(temp);
                    estado = 0;
                    lexema = "";
                }
            }
        }
    }

    public Token getCurrentToken() {
        Token tem = this.array[this.count];
        count++;
        return tem;
    }

}
