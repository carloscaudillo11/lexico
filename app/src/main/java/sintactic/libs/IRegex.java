package sintactic.libs;

public interface IRegex {
    String OPERADOR = "(\\+|\\-|\\=)";
    String DELIMITADOR = "(\\{|\\}|\\;|\\(|\\))";
    String DIGITO = "([0-9])";
    String ENTERO = "([0-9]+)";
    String FLOTANTE = "(^-?\\d+(?:.\\d+)?$)";
    String ID = "(^[a-z][a-z0-9]*)";
    String LETRA = "([a-z])";
    String MAYUSCULA = "([A-Z])";
    String RESERVADA = "(Program|Int|Double|Print)";
    String ALFABETO  = "([a-zA-Z0-9]|\\+|\\-|\\=|\\(|\\)|\\;|\\{|\\})";
}
