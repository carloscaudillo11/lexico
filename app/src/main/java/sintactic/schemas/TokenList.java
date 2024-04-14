package sintactic.schemas;

import sintactic.models.Token;
import java.util.Iterator;

class Node<T> {
    private T data;
    private Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}

public class TokenList {
    protected Node<Token> start, end;

    public TokenList() {
        start = null;
        end = null;
    }

    public boolean isEmpty() {
        return start == null;
    }

    public void add(Token token) {
        if (!isEmpty()) {
            end.setNext(new Node<>(token));
            end = end.getNext();
        } else {
            start = end = new Node<>(token);
        }
    }

    public void printTokens() {
        Node<Token> recorrer = start;
        while (recorrer != null) {
            Token token = recorrer.getData();
            System.out.printf("%10s%20s%23s%8s%8s%n",
                    token.lexema(),
                    token.token(),
                    token.descripcion(),
                    token.atributo(),
                    token.linea());
            recorrer = recorrer.getNext();
        }
    }

    public Iterator<Token> iterator() {
        return new Iterator<>() {
            private Node<Token> iteratorNode = start;

            @Override
            public boolean hasNext() {
                return iteratorNode != null;
            }

            @Override
            public Token next() {
                Token item = iteratorNode.getData();
                iteratorNode = iteratorNode.getNext();
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void printErrors() {
        Node<Token> recorrer = start;
        while (recorrer != null) {
            Token token = recorrer.getData();
            System.out.printf("\u001B[31m" + token.lexema() + " "
                    + token.descripcion() + "\u001B[0m");
            System.out.println("\n");
            recorrer = recorrer.getNext();
        }
    }
}
