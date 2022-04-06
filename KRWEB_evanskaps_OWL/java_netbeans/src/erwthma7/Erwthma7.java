
package erwthma7;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import static com.hp.hpl.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.util.FileManager;
import java.util.Iterator;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.openjena.atlas.io.IndentedWriter;


public class Erwthma7 extends javax.swing.JFrame {

    public Erwthma7() {
        initComponents();
        jComboBox2.addItem("");
        jComboBox1.addItem("");
        // model load and validation check
        Model data = FileManager.get().loadModel("movies_onto.owl");
        OntModel infmodel = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, data );
        ValidityReport validity = infmodel.validate();
        if (validity.isValid()){
            System.out.print("OK");
        } else { 
            System.out.print("Conflicts");
            for (Iterator i = validity.getReports(); i.hasNext();){
                System.out.print("-" + i.next());
            }
        }
    /////////////// fill drop down menu with actor names///////////////////////////
        String actor_names;
        actor_names = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?x rdf:type movies:Actor ."
                + "?x movies:name ?name ."
                + "}";
        Query query_actor_names = QueryFactory.create(actor_names) ;
        query_actor_names.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec = QueryExecutionFactory.create(query_actor_names, infmodel) ;
        ResultSet rs_actor_names = qexec.execSelect() ;
        for ( ; rs_actor_names.hasNext() ; ) {
            QuerySolution sol = rs_actor_names.nextSolution();
            RDFNode name = sol.get("name") ;
            jComboBox1.addItem(name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
    

    /////////////// fill drop down menu with movie genres///////////////////////////
        String movie_genres;
        movie_genres = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT DISTINCT ?genre "
                + "WHERE {"
                + "?x rdf:type movies:Movie ;"
                + "movies:has_genre ?genre ."
                + "}";
        Query movie_genres_query = QueryFactory.create(movie_genres) ;
        movie_genres_query.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec2 = QueryExecutionFactory.create(movie_genres_query,infmodel);
        ResultSet rs_movie_genres = qexec2.execSelect() ;
        for ( ; rs_movie_genres.hasNext() ; ) {
            QuerySolution sol = rs_movie_genres.nextSolution();
            RDFNode genre = sol.get("genre") ;
            jComboBox2.addItem(genre.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#",""));
        }
        
    
    
    
    
    
    
    }

    private void filter (String query){
        ////FILTER movie_table WITH PARAMETER DROPDOWN MENU SELECTED VALUE (GENRE)
        DefaultTableModel tblMode_filter_movie = (DefaultTableModel)movie_table.getModel();
        TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(tblMode_filter_movie);
        movie_table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
      private void filter_actor (String query){
        ////FILTER movie_table WITH PARAMETER DROPDOWN MENU SELECTED VALUE (ACTORS)
        Model data = FileManager.get().loadModel("movies_onto.owl");
        InfModel infmodel = ModelFactory.createRDFSModel(data);
        DefaultTableModel aModel = (DefaultTableModel) movie_table.getModel();
        aModel.setRowCount(0); // first clear existing table contents
        String filter_by_actor = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
                + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
                + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
                + "\nSELECT ?title ?rating ?date ?genre "
                + "\nWHERE { "
                + "\n?x rdf:type movies:Movie ; "
                + "\nmovies:title ?title ;"
                + "\nmovies:rating ?rating ;"
                + "\nmovies:release_date ?date ;"
                + "\nmovies:has_genre ?genre ;"
                + "\nmovies:features_actor ?y ."
                +"\n?y rdf:type movies:Actor ;"
                +"\nmovies:name ?name ."
                + "\nFILTER(?name = '%s') \n}" , query);
        Query movie_details_query = QueryFactory.create(filter_by_actor) ;
        movie_details_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec1 = QueryExecutionFactory.create(movie_details_query, infmodel) ;
        ResultSet movie_details_rs= qexec1.execSelect() ;
        for ( ; movie_details_rs.hasNext() ; ) {
            QuerySolution sol1 = movie_details_rs.nextSolution();
            RDFNode mov_title = sol1.get("title") ;
            RDFNode mov_rate = sol1.get("rating") ;
            RDFNode mov_release = sol1.get("date");
            RDFNode mov_genre = sol1.get("genre");
            aModel.addRow(new Object[]{ mov_title.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_rate.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_release.toString().replace("^^http://www.w3.org/2001/XMLSchema#date",""),mov_genre.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#","")});
            movie_table.setModel(aModel); 
        }
    }  
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        movie_table = new javax.swing.JTable();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        movie_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Movie Title", "Rating", "Release date", "Genre"
            }
        ));
        jScrollPane1.setViewportView(movie_table);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Search by actor");

        jLabel2.setText("Search by genre");

        jButton1.setText("Add new");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("More details");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox1, 0, 223, Short.MAX_VALUE)
                                        .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jButton2)
                        .addContainerGap(73, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 85, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
       
        String selectedValue = jComboBox1.getSelectedItem().toString();
        if (selectedValue == ""){
            ////////////////////QUERY TO FILL PROFESOR TABLE /////////////////////////       
            DefaultTableModel aModel = (DefaultTableModel) movie_table.getModel();
            aModel.setRowCount(0); // first clear existing table contents
            Model data = FileManager.get().loadModel("movies_onto.owl");
            InfModel infmodel = ModelFactory.createRDFSModel(data);
            String movie_details;
            movie_details = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                    + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                    + "SELECT ?title ?rating ?date ?genre "
                    + "WHERE {"
                    + "?x rdf:type movies:Movie ;"
                    + "movies:title ?title ;"
                    + "movies:rating ?rating ;"
                    + "movies:release_date ?date ;"
                    + "movies:has_genre ?genre ."
                    + "}";
            Query movie_details_query = QueryFactory.create(movie_details) ;
            movie_details_query.serialize(new IndentedWriter(System.out,false)) ;
            QueryExecution qexec1 = QueryExecutionFactory.create(movie_details_query, infmodel) ;
            ResultSet movie_details_rs= qexec1.execSelect() ;
            for ( ; movie_details_rs.hasNext() ; ) {
                QuerySolution sol1 = movie_details_rs.nextSolution();
                RDFNode mov_title = sol1.get("title") ;
                RDFNode mov_rate = sol1.get("rating") ;
                RDFNode mov_release = sol1.get("date");
                RDFNode mov_genre = sol1.get("genre");
                aModel.addRow(new Object[]{ mov_title.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_rate.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_release.toString().replace("^^http://www.w3.org/2001/XMLSchema#date",""),mov_genre.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#","")});
                movie_table.setModel(aModel); 

            }
    ////////////////////////////////////////////////////////////////////////
        }else{
        filter_actor(selectedValue);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        //GET DROP DOWN MENU SELECTED VALUE TO STRING
        String selectedValue = jComboBox2.getSelectedItem().toString();
       //CALL FILTER FUNCTION 
       if (selectedValue == " "){
        ////////////////////QUERY TO FILL PROFESOR TABLE /////////////////////////       
        DefaultTableModel aModel = (DefaultTableModel) movie_table.getModel();
        aModel.setRowCount(0); // first clear existing table contents
        Model data = FileManager.get().loadModel("movies_onto.owl");
        InfModel infmodel = ModelFactory.createRDFSModel(data);
        String movie_details;
        movie_details = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?title ?rating ?date ?genre "
                + "WHERE {"
                + "?x rdf:type movies:Movie ;"
                + "movies:title ?title ;"
                + "movies:rating ?rating ;"
                + "movies:release_date ?date ;"
                + "movies:has_genre ?genre ."
                + "}";
        Query movie_details_query = QueryFactory.create(movie_details) ;
        movie_details_query.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec1 = QueryExecutionFactory.create(movie_details_query, infmodel) ;
        ResultSet movie_details_rs= qexec1.execSelect() ;
        for ( ; movie_details_rs.hasNext() ; ) {
            QuerySolution sol1 = movie_details_rs.nextSolution();
            RDFNode mov_title = sol1.get("title") ;
            RDFNode mov_rate = sol1.get("rating") ;
            RDFNode mov_release = sol1.get("date");
            RDFNode mov_genre = sol1.get("genre");
            aModel.addRow(new Object[]{ mov_title.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_rate.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""),mov_release.toString().replace("^^http://www.w3.org/2001/XMLSchema#date",""),mov_genre.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#","")});
            movie_table.setModel(aModel); 

        }
    ////////////////////////////////////////////////////////////////////////
       }
       else{
        String filter_prof = selectedValue;
        filter(filter_prof);
       }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        add_new c = new add_new();
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        more_details c = new more_details();
        c.setVisible(true);
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
            java.util.logging.Logger.getLogger(Erwthma7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Erwthma7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Erwthma7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Erwthma7.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Erwthma7().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable movie_table;
    // End of variables declaration//GEN-END:variables
}
