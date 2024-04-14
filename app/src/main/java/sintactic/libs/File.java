package sintactic.libs;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class File {
    String[] lines;
    java.io.File file;

    public void openFile(String ruta) {
        file = new java.io.File(ruta);
        if (!file.exists()) {
            JFileChooser promptFile = new JFileChooser();
            promptFile.setFileFilter(new FileNameExtensionFilter("Documentos", "txt"));
            int option = promptFile.showDialog(null, "Seleccionar");
            System.out.println("Nueva ruta: "+option);
            file = new java.io.File(promptFile.getSelectedFile().toString());
        }
    }

    public void writeFile(String data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void countLines() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int i = 0;
        while (br.readLine() != null)
            i++;
        lines = new String[i];
    }

    public String[] readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            countLines();
            while ((line = br.readLine()) != null) {
                lines[i] = line;
                i++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
     
}
