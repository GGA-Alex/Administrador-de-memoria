package memoria;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;

public class MemoriaFrame extends javax.swing.JFrame {

    ArrayList<Archivo> archivos;
    DefaultTableModel modelo;
    
    public MemoriaFrame() {
        initComponents();
        
        archivos = new ArrayList<Archivo>();
        
        createFiles();
        
        modelo = (DefaultTableModel) tb_memoria.getModel();
    }
    
    public boolean createFiles(){
        try {
            for (int i = 0; i < 32; i++) {
                int random = (int) (Math.random() * (2100 - 300 + 1) + 300);
                RandomAccessFile f = new RandomAccessFile("archivos/archivo" + i + ".txt", "rw");
                f.setLength(1024 * random);
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
    public boolean readFolderFiles(String folder) {
        boolean resultado;
        File folderFile = new File(folder);
        if ((resultado = folderFile.exists())) {
            File[] files = folderFile.listFiles();
            for (File file : files) {
                Archivo archivoNuevo = new Archivo(file.getName(), file.length() / 1024);
                archivos.add(archivoNuevo);
            }
        }
        return resultado;
    }
    
    public void primerAjuste(){
        long[] memoriaTotal = {1700, 1500, 900, 5000, 700, 2000, 1800, 3000, 1000, 1200, 2400, 4000};
        String memoriaResultados[] = new String[12];
        
        ArrayList<Archivo> archivosVisitados = new ArrayList<>();
        
        archivos.clear();
        readFolderFiles("archivos");
        Collections.sort(archivos);
        
        for(int i = 0; i < 2; i++){
            
            for (Archivo archivo : archivos) {
                
                for(int j = 0; j < 12; j++){
                    
                    if(archivo.getTamanio() <= memoriaTotal[j]){
                        memoriaTotal[j] = memoriaTotal[j] - archivo.getTamanio();
                        if(memoriaResultados[j] == null){
                            memoriaResultados[j] = "\n**********\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb \n----------";
                        }else{
                            memoriaResultados[j] = memoriaResultados[j] + "\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb\n----------";
                        }
                        archivosVisitados.add(archivo);
                        break;
                    }
                    
                }
                
            }
            
            for(int k = 0; k < archivosVisitados.size(); k++){
                Iterator<Archivo> iterador = archivos.iterator();
                while (iterador.hasNext()) {
                    Archivo key = iterador.next();
                    if (key.equals(archivosVisitados.get(k))){
                        iterador.remove();
                    }
                }
            }
        }
        
        String resultado = "**********";
        for(int i = 0; i < 12; i++) {
            resultado = resultado + "\nEspacio " + (i+1) + memoriaResultados[i] + "\n" + memoriaTotal[i] + " kb libres \n**********";
        }
        
        String noVisitados = "";
        for(int i = 0; i < archivos.size(); i++){
            noVisitados = noVisitados + archivos.get(i).toString() + "\n";
        }
        
        txt_resultado.setText(resultado);
        txt_noVisitados.setText(noVisitados);
        memoriaRestante(memoriaTotal);
    }
    
    public void mejorAjuste()   {
        long[] memoriaTotal = {1700, 1500, 900, 5000, 700, 2000, 1800, 3000, 1000, 1200, 2400, 4000};
        String memoriaResultados[] = new String[12];
        
        ArrayList<Archivo> archivosVisitados = new ArrayList<>();
        ArrayList<Long> totalesObtenidos = new ArrayList<>();
        
        archivos.clear();
        readFolderFiles("archivos");
        Collections.sort(archivos);
        
        int posicion;
        
        for(int i = 0; i < 2; i++){ // Repetimos el ciclo dos veces
            
            for (Archivo archivo : archivos) {
                totalesObtenidos.clear();
                
                for(int j = 0; j < 12; j++){
                    if ((memoriaTotal[j] - archivo.getTamanio()) >= 0)
                        totalesObtenidos.add(memoriaTotal[j] - archivo.getTamanio());
                    else{
                        totalesObtenidos.add(Long.MAX_VALUE);
                    }
                }
                posicion = totalesObtenidos.indexOf(Collections.min(totalesObtenidos));
                
                if(Collections.min(totalesObtenidos) != 9223372036854775807L){
                    memoriaTotal[posicion] = memoriaTotal[posicion] - archivo.getTamanio();
                    if(memoriaResultados[posicion] == null){
                        memoriaResultados[posicion] = "\n**********\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb \n----------";
                    }else{
                        memoriaResultados[posicion] = memoriaResultados[posicion] + "\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb\n----------";
                    }
                    archivosVisitados.add(archivo);
                }
            }
            
            for(int k = 0; k < archivosVisitados.size(); k++){
                Iterator<Archivo> iterador = archivos.iterator();
                while (iterador.hasNext()) {
                    Archivo key = iterador.next();
                    if (key.equals(archivosVisitados.get(k))){
                        iterador.remove();
                    }
                }
            }
        }
        
        String resultado = "**********";
        for(int i = 0; i < 12; i++) {
            resultado = resultado + "\nEspacio " + (i+1) + memoriaResultados[i] + "\n" + memoriaTotal[i] + " kb libres \n**********";
        }
        
        String noVisitados = "";
        for(int i = 0; i < archivos.size(); i++){
            noVisitados = noVisitados + archivos.get(i).toString() + "\n";
        }
        
        txt_resultado.setText(resultado);
        txt_noVisitados.setText(noVisitados);
        memoriaRestante(memoriaTotal);
    }
    
    public void peorAjuste(){
       long[] memoriaTotal = {1700, 1500, 900, 5000, 700, 2000, 1800, 3000, 1000, 1200, 2400, 4000};
        String memoriaResultados[] = new String[12];
        
        ArrayList<Archivo> archivosVisitados = new ArrayList<>();
        ArrayList<Long> totalesObtenidos = new ArrayList<>();
        
        archivos.clear();
        readFolderFiles("archivos");
        Collections.sort(archivos);
        
        int posicion;
        
        for(int i = 0; i < 2; i++){
            
            for (Archivo archivo : archivos) {
                totalesObtenidos.clear();
                
                for(int j = 0; j < 12; j++){
                    
                    if ((memoriaTotal[j] - archivo.getTamanio()) >= 0)
                        totalesObtenidos.add(memoriaTotal[j] - archivo.getTamanio());
                    else{
                        totalesObtenidos.add(Long.MIN_VALUE);
                    }
                }
                posicion = totalesObtenidos.indexOf(Collections.max(totalesObtenidos));
                if(Collections.max(totalesObtenidos) != -9223372036854775808L){
                    memoriaTotal[posicion] = memoriaTotal[posicion] - archivo.getTamanio();
                    if(memoriaResultados[posicion] == null){
                        memoriaResultados[posicion] = "\n**********\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb \n----------";
                    }else{
                        memoriaResultados[posicion] = memoriaResultados[posicion] + "\n" + archivo.getNombre() + ": " + archivo.getTamanio() + " kb\n----------";
                    }
                    archivosVisitados.add(archivo);
                }
                
            }
            
            for(int k = 0; k < archivosVisitados.size(); k++){
                Iterator<Archivo> iterador = archivos.iterator();
                while (iterador.hasNext()) {
                    Archivo key = iterador.next();
                    if (key.equals(archivosVisitados.get(k))){
                        iterador.remove();
                    }
                }
            }
        }
        
        String resultado = "**********";
        for(int i = 0; i < 12; i++) {
            resultado = resultado + "\nEspacio " + (i+1) + memoriaResultados[i] + "\n" + memoriaTotal[i] + " kb libres \n**********";
        }
        
        String noVisitados = "";
        for(int i = 0; i < archivos.size(); i++){
            noVisitados = noVisitados + archivos.get(i).toString() + "\n";
        }
        
        txt_resultado.setText(resultado);
        txt_noVisitados.setText(noVisitados);
        memoriaRestante(memoriaTotal); 
    }
    
    // Memoria obtenida al fianl de cada ajuste
    public void memoriaRestante(long[] memoria){
        modelo.setRowCount(0);
        for(int  i = 0; i < 12; i++) {
            modelo.addRow(new Object[]{"Espacio " + (i + 1), memoria[i] + " kb"});
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_memoria = new javax.swing.JTable();
        cb_opcion = new javax.swing.JComboBox<>();
        btn_ejecutar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_resultado = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_noVisitados = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb_memoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Espacio", "Memoria libre"
            }
        ));
        jScrollPane1.setViewportView(tb_memoria);

        cb_opcion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Primer Ajuste", "Mejor Ajuste", "Peor Ajuste" }));

        btn_ejecutar.setText("Ejecutar");
        btn_ejecutar.setToolTipText("");
        btn_ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ejecutarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMINISTRADOR DE MEMORIA");

        txt_resultado.setColumns(20);
        txt_resultado.setRows(5);
        jScrollPane2.setViewportView(txt_resultado);

        txt_noVisitados.setColumns(20);
        txt_noVisitados.setRows(5);
        jScrollPane3.setViewportView(txt_noVisitados);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ARCHIVOS NO VISITADOS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cb_opcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_opcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_ejecutar))
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ejecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ejecutarActionPerformed
        if(cb_opcion.getSelectedItem().equals("Primer Ajuste")){
            primerAjuste();
        }
        
        if(cb_opcion.getSelectedItem().equals("Mejor Ajuste")){
            mejorAjuste();
        }
        
        if(cb_opcion.getSelectedItem().equals("Peor Ajuste")){
            peorAjuste();
        }
    }//GEN-LAST:event_btn_ejecutarActionPerformed

    
    public static void main(String args[]) {
        /* Set the windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MemoriaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemoriaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemoriaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemoriaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MemoriaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ejecutar;
    private javax.swing.JComboBox<String> cb_opcion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tb_memoria;
    private javax.swing.JTextArea txt_noVisitados;
    private javax.swing.JTextArea txt_resultado;
    // End of variables declaration//GEN-END:variables
}
