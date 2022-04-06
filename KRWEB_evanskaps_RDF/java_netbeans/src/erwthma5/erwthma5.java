package erwthma5;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.query.Query ;
import com.hp.hpl.jena.query.QueryExecution ;
import com.hp.hpl.jena.query.QueryExecutionFactory ;
import com.hp.hpl.jena.query.QueryFactory ;
import com.hp.hpl.jena.query.QuerySolution ;
import com.hp.hpl.jena.query.ResultSet ;
import com.hp.hpl.jena.reasoner.ValidityReport;
import org.openjena.atlas.io.IndentedWriter ;
import com.hp.hpl.jena.util.FileManager;
import java.io.*;
import java.util.Iterator;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.*;
/**
 *
 * @author Ευάγγελος Καψουλάκης 1047062
 */
public class erwthma5 extends javax.swing.JFrame {

    /**
     * Creates new form erwthma5
     */
    public erwthma5() {
        initComponents();
                ////////////// VALIDATOR ///////////////////////////////
        Model data = FileManager.get().loadModel("erwthma3.rdf");
        InfModel infmodel = ModelFactory.createRDFSModel(data);
        ValidityReport validity = infmodel.validate();
        if (validity.isValid()){
            System.out.print("OK");
        } else { 
            System.out.print("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext();){
                System.out.print("-" + i.next());
            }
        }
        //////////////////////////////////////////////////////////
      
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open("erwthma3.rdf");
       if (in != null) {
        model.read(in, null, "");
        model.write(System.out, "");
        } else {
            System.err.println("cannot read " + "erwthma3.rdf");;
        }
     
       ////////// QUERY TO FILL DROP DOWN MENU WITH DEP NAMES /////////////////
        String dep_names_queryString;
        dep_names_queryString = "PREFIX uni: <http://www.mydomain.org/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "SELECT ?y "
                + "WHERE {"
                + "?x rdf:type <uni:Department>."
                + "?x uni:dep_name ?y."
                + "}";
        Query query_dep_names = QueryFactory.create(dep_names_queryString) ;
        query_dep_names.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec = QueryExecutionFactory.create(query_dep_names, model) ;
        ResultSet rs_dep_names = qexec.execSelect() ;
        for ( ; rs_dep_names.hasNext() ; ) {
            QuerySolution sol = rs_dep_names.nextSolution();
            RDFNode y = sol.get("y") ;
            jComboBox1.addItem(y.toString());
        }
    //////////////////////////////////////////////////////////////////////////
    ////////////////////QUERY TO FILL PROFESOR TABLE /////////////////////////
        DefaultTableModel aModel = (DefaultTableModel) ProfTable.getModel();
        String prof_queryString;
        prof_queryString = "PREFIX uni: <http://www.mydomain.org/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "SELECT ?name ?mail ?age ?phone ?member ?teach "
                + "WHERE {"
                + "?x rdf:type <uni:Professor> ."
                + "?x uni:has_name ?name."
                + "?x uni:has_email ?mail ."
                + "?x uni:has_age ?age."
                + "?x uni:has_phone ?phone."
                + "?x uni:member_of ?member."
                + "?x uni:teaches ?teach."
                + "}";
        Query query_prof = QueryFactory.create(prof_queryString) ;
        query_prof.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec1 = QueryExecutionFactory.create(query_prof, model) ;
        ResultSet rs_prof= qexec1.execSelect() ;
        for ( ; rs_prof.hasNext() ; ) {
            QuerySolution sol1 = rs_prof.nextSolution();
            RDFNode name = sol1.get("name") ;
            RDFNode mail = sol1.get("mail") ;
            RDFNode age = sol1.get("age");
            RDFNode phone = sol1.get("phone");
            RDFNode member = sol1.get("member");
            RDFNode teach = sol1.get("teach");
            aModel.addRow(new Object[]{ name,mail,age,phone,member,teach});
            ProfTable.setModel(aModel); 

        }
    ////////////////////////////////////////////////////////////////////////
    /////////////////////QUERY TO FILL STUDENT TABLE /////////////////////////
        DefaultTableModel aModel1 = (DefaultTableModel) StudentTable.getModel();
        String student_queryString;
        student_queryString = "PREFIX uni: <http://www.mydomain.org/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "SELECT ?name ?mail ?age ?phone ?member "
                + "WHERE {"
                + "?x rdf:type <uni:Student> ."
                + "?x uni:has_name ?name."
                + "?x uni:has_email ?mail ."
                + "?x uni:has_age ?age."
                + "?x uni:has_phone ?phone."
                + "?x uni:member_of ?member."
                + "}";
        Query query_student = QueryFactory.create(student_queryString) ;
        query_student.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec2 = QueryExecutionFactory.create(query_student, model) ;
        ResultSet rs_student = qexec2.execSelect() ;
        for ( ; rs_student.hasNext() ; ) {
            QuerySolution sol1 = rs_student.nextSolution();
            RDFNode name = sol1.get("name") ;
            RDFNode mail = sol1.get("mail") ;
            RDFNode age = sol1.get("age");
            RDFNode phone = sol1.get("phone");
            RDFNode member = sol1.get("member");
            RDFNode teach = sol1.get("teach");
            aModel1.addRow(new Object[]{ name,mail,age,phone,member});
            StudentTable.setModel(aModel1); 

        }
    /////////////////////QUERY TO FILL CLASSROOM TABLE /////////////////////////    
        DefaultTableModel aModel2 = (DefaultTableModel) ClassTable.getModel();
        String classroom_queryString;
        classroom_queryString = "PREFIX uni: <http://www.mydomain.org/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "SELECT ?name ?capacity ?department "
                + "WHERE {"
                + "?x rdf:type <uni:Classroom> ."
                + "?x uni:room_name ?name."
                + "?x uni:room_capacity ?capacity ."
                + "?x uni:room_department ?department."
                + "}";
        Query query_classroom = QueryFactory.create(classroom_queryString) ;
        query_classroom.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec3 = QueryExecutionFactory.create(query_classroom, model) ;
        ResultSet rs_classroom = qexec3.execSelect() ;
        for ( ; rs_classroom.hasNext() ; ) {
            QuerySolution sol1 = rs_classroom.nextSolution();
            RDFNode name = sol1.get("name") ;
            RDFNode capacity = sol1.get("capacity") ;
            RDFNode department = sol1.get("department");
            aModel2.addRow(new Object[]{ name,capacity,department});
            ClassTable.setModel(aModel2); 

        }
     /////////////////////////////////////////////////////////////////////////// 
     /////////////////////QUERY TO FILL LESSONS TABLE /////////////////////////  
        DefaultTableModel aModel3 = (DefaultTableModel) LessonTable.getModel();
        String Lesson_queryString;
        // LESSONS DON'T HAVE RDF:TYPE FROM QUESTION 3 SO WE START FROM PROFESSOR
        Lesson_queryString = "PREFIX uni: <http://www.mydomain.org/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "SELECT ?lesson ?prof ?depart "
                + "WHERE {"
                + "?x rdf:type <uni:Professor> ."
                + "?x uni:has_name ?prof."
                + "?x uni:member_of ?depart."
                + "?y uni:taught_by ?prof."
                + "?y uni:les_name ?lesson."
                + "}";
        Query query_lesson = QueryFactory.create(Lesson_queryString) ;
        query_lesson.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec4 = QueryExecutionFactory.create(query_lesson, model) ;
        ResultSet rs_lesson = qexec4.execSelect() ;
        for ( ; rs_lesson.hasNext() ; ) {
            QuerySolution sol1 = rs_lesson.nextSolution();
            RDFNode lesson = sol1.get("lesson") ;
            RDFNode professor = sol1.get("prof") ;
            RDFNode department = sol1.get("depart");
            aModel3.addRow(new Object[]{lesson,professor,department});
            LessonTable.setModel(aModel3); 

        }
    }
    private void filter (String query){
        ////FILTER professor TABLE WITH PARAMETER DROPDOWN MENU SELECTED VALUE 
        DefaultTableModel tblMode_filter_prof = (DefaultTableModel)ProfTable.getModel();
        TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(tblMode_filter_prof);
        ProfTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
        /// FILTER STUDENT TABLE WITH PARAMETER DROPDOWN MENU SELECTED VALUE 
        DefaultTableModel tblMode_filter_student = (DefaultTableModel)StudentTable.getModel();
        TableRowSorter<DefaultTableModel> tr1=new TableRowSorter<DefaultTableModel>(tblMode_filter_student);
        StudentTable.setRowSorter(tr1);
        tr1.setRowFilter(RowFilter.regexFilter(query));
        ///// FILTER CLASSROOM TABLE WITH PARAMETER DROPDOWN MENU SELECTED VALUE 
        DefaultTableModel tblMode_filter_classroom = (DefaultTableModel)ClassTable.getModel();
        TableRowSorter<DefaultTableModel> tr2=new TableRowSorter<DefaultTableModel>(tblMode_filter_classroom);
        ClassTable.setRowSorter(tr2);
        tr2.setRowFilter(RowFilter.regexFilter(query));
        /////// FILTER LESSONS TABLE WITH PARAMETER DROPDOWN MENU SELECTED VALUE 
        DefaultTableModel tblMode_filter_lesson = (DefaultTableModel)LessonTable.getModel();
        TableRowSorter<DefaultTableModel> tr3=new TableRowSorter<DefaultTableModel>(tblMode_filter_lesson);
        LessonTable.setRowSorter(tr3);
        tr3.setRowFilter(RowFilter.regexFilter(query));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        StudentTable = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ProfTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ClassTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LessonTable = new javax.swing.JTable();
        add_new_btn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        StudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "email", "Age", "phone", "Department"
            }
        ));
        jScrollPane1.setViewportView(StudentTable);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Professor Table Details");

        jLabel2.setText("Student table details");

        ProfTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "email", "Age", "phone", "Department", "Teaching"
            }
        ));
        jScrollPane2.setViewportView(ProfTable);
        if (ProfTable.getColumnModel().getColumnCount() > 0) {
            ProfTable.getColumnModel().getColumn(5).setHeaderValue("Teaching");
        }

        jLabel3.setText("Classrooms details");

        ClassTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Classroom Name", "Capacity", "Department"
            }
        ));
        jScrollPane3.setViewportView(ClassTable);

        jLabel4.setText("Lessons details");

        LessonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lesson ", "Professor", "Department"
            }
        ));
        jScrollPane4.setViewportView(LessonTable);

        add_new_btn.setText("ADD NEW RESOURCE");
        add_new_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_new_btnActionPerformed(evt);
            }
        });

        jButton2.setText("URI SEARCH");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(add_new_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 36, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_new_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        //GET DROP DOWN MENU SELECTED VALUE TO STRING
        String selectedValue = jComboBox1.getSelectedItem().toString();
       //CALL FILTER FUNCTION 
        String filter_prof = selectedValue;
        filter(filter_prof);
    }//GEN-LAST:event_jComboBox1ActionPerformed
   
    private void add_new_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_new_btnActionPerformed
        Add_new_property n = new Add_new_property();
        n.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_add_new_btnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ypoerwthmaG g = new ypoerwthmaG();
        g.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(erwthma5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(erwthma5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(erwthma5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(erwthma5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new erwthma5().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ClassTable;
    private javax.swing.JTable LessonTable;
    private javax.swing.JTable ProfTable;
    private javax.swing.JTable StudentTable;
    private javax.swing.JButton add_new_btn;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
