/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package erwthma7;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import static com.hp.hpl.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.shared.NoWriterForLangException;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL2;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openjena.atlas.io.IndentedWriter;

/**
 *
 * @author evans
 */
public class edit_movie extends javax.swing.JFrame {

    /**
     * Creates new form edit_movie
     */
    public edit_movie() {
        initComponents();
        Model data = FileManager.get().loadModel("movies_onto.owl");
        OntModel infmodel = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, data );
        jComboBox3.addItem("None");
        //////////// fill drown down menu with all movies in owl ////////////////////////////////
        String movies_names;
        movies_names = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?title "
                + "WHERE {"
                + "?x rdf:type movies:Movie ."
                + "?x movies:title ?title ."
                + "}";
        Query query_movies_names = QueryFactory.create(movies_names) ;
        query_movies_names.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec = QueryExecutionFactory.create(query_movies_names, infmodel) ;
        ResultSet rs_movies_names = qexec.execSelect() ;
        for ( ; rs_movies_names.hasNext() ; ) {
            QuerySolution sol = rs_movies_names.nextSolution();
            RDFNode name = sol.get("title") ;
            jComboBox1.addItem(name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////// fill drop down menu with actor names///////////////////////////
        String actors_names;
        actors_names = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?x rdf:type movies:Actor ."
                + "?x movies:name ?name ."
                + "}";
        Query query_actors_names = QueryFactory.create(actors_names) ;
        query_actors_names.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec1 = QueryExecutionFactory.create(query_actors_names, infmodel) ;
        ResultSet rs_actors_names = qexec1.execSelect() ;
        for ( ; rs_actors_names.hasNext() ; ) {
            QuerySolution sol1 = rs_actors_names.nextSolution();
            RDFNode actor_name = sol1.get("name") ;
            jComboBox2.addItem(actor_name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
            /////////////// fill drop down menu with awards///////////////////////////
        String awards;
        awards = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?x "
                + "WHERE {"
                + "?x rdf:type movies:MovieAward ."
                + "}";
        Query query_awards = QueryFactory.create(awards) ;
        query_awards.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec2 = QueryExecutionFactory.create(query_awards, infmodel) ;
        ResultSet rs_awards = qexec2.execSelect() ;
        for ( ; rs_awards.hasNext() ; ) {
            QuerySolution sol = rs_awards.nextSolution();
            RDFNode award_name = sol.get("x") ;
            jComboBox3.addItem(award_name.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#","").replace("_", " "));
        }
                    /////////////// fill drop down menu with directors///////////////////////////
        String directors;
        directors = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?x rdf:type movies:Director ."
                + "?x movies:name ?name ."
                + "}";
        Query query_directors = QueryFactory.create(directors) ;
        query_awards.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec3 = QueryExecutionFactory.create(query_directors, infmodel) ;
        ResultSet rs_directors = qexec3.execSelect() ;
        for ( ; rs_directors.hasNext() ; ) {
            QuerySolution sol = rs_directors.nextSolution();
            RDFNode director_name = sol.get("name") ;
            jComboBox4.addItem(director_name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
        /////////////// fill drop down menu with Distributor companies///////////////////////////
        String distributors;
        distributors = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?x "
                + "WHERE {"
                + "?x rdf:type movies:Distributor ."
                + "}";
        Query query_distributors = QueryFactory.create(distributors) ;
        query_distributors.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec4 = QueryExecutionFactory.create(query_distributors, infmodel) ;
        ResultSet rs_distributors = qexec4.execSelect() ;
        for ( ; rs_distributors.hasNext() ; ) {
            QuerySolution sol = rs_distributors.nextSolution();
            RDFNode name = sol.get("x") ;
            jComboBox5.addItem(name.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#","").replace("_", " "));
        }
        /////////////// fill drop down menu with script writters///////////////////////////
        String writters;
        writters = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?x rdf:type movies:Writer ."
                + "?x movies:name ?name ."
                + "}";
        Query query_writters = QueryFactory.create(writters) ;
        query_writters.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec5 = QueryExecutionFactory.create(query_writters, infmodel) ;
        ResultSet rs_writters = qexec5.execSelect() ;
        for ( ; rs_writters.hasNext() ; ) {
            QuerySolution sol = rs_writters.nextSolution();
            RDFNode name = sol.get("name") ;
            jComboBox6.addItem(name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
        /////////////// fill drop down menu with soundtrack composser///////////////////////////
        String composers;
        composers = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?x rdf:type movies:Composer ."
                + "?x movies:name ?name ."
                + "}";
        Query query_composers = QueryFactory.create(composers) ;
        query_composers.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec6 = QueryExecutionFactory.create(query_composers, infmodel) ;
        ResultSet rs_composers = qexec6.execSelect() ;
        for ( ; rs_composers.hasNext() ; ) {
            QuerySolution sol = rs_composers.nextSolution();
            RDFNode name = sol.get("name") ;
            jComboBox7.addItem(name.toString().replace("^^http://www.w3.org/2001/XMLSchema#string",""));
        }
        /////////////// fill drop down menu with languages///////////////////////////
        String languages;
        languages = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#>"
                + "PREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#>"
                + "SELECT ?x "
                + "WHERE {"
                + "?x rdf:type movies:Languages ."
                + "}";
        Query query_languages = QueryFactory.create(languages) ;
        query_languages.serialize(new IndentedWriter(System.out,false)) ;
        QueryExecution qexec7 = QueryExecutionFactory.create(query_languages, infmodel) ;
        ResultSet rs_languages = qexec7.execSelect() ;
        for ( ; rs_languages.hasNext() ; ) {
            QuerySolution sol = rs_languages.nextSolution();
            RDFNode name = sol.get("x") ;
            jComboBox8.addItem(name.toString().replace("http://www.semanticweb.org/evans/ontologies/movies#",""));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jComboBox8 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Select the movie you want to edit:");

        jLabel2.setText("Add actor featured in movie:");

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Add an award for movie:");

        jLabel4.setText("Add a movie director:");

        jLabel5.setText("Movie distributed by:");

        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel6.setText("Add a writter of the script of movie:");

        jLabel7.setText("Add a composer of the soundtrack:");

        jLabel8.setText("Add a movie language:");

        jButton2.setText("Edit");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox8, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 257, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        add_new back = new add_new();
        back.setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Model data = FileManager.get().loadModel("movies_onto.owl");
        OntModel infmodel = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, data );
        String movie_title = jComboBox1.getSelectedItem().toString();
        String movie = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
        + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
        + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
        + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
        + "\nSELECT ?x "
        + "\nWHERE { "
        + "\n?x rdf:type movies:Movie ; "
        + "\nmovies:title ?title ;"
        + "\nFILTER(?title = '%s') \n}" , movie_title);   
        Query movie_query = QueryFactory.create(movie) ;
        movie_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec = QueryExecutionFactory.create(movie_query, infmodel) ;
        ResultSet movie_rs= qexec.execSelect() ;
        QuerySolution sol = movie_rs.nextSolution();
        RDFNode movie_individual = sol.get("x") ;
        
        String movie_actor_name = jComboBox2.getSelectedItem().toString();
        String movie_actor = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
        + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
        + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
        + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
        + "\nSELECT ?x "
        + "\nWHERE { "
        + "\n?x rdf:type movies:Actor ; "
        + "\nmovies:name ?name ;"
        + "\nFILTER(?name = '%s') \n}" , movie_actor_name);   
        Query movie_actor_query = QueryFactory.create(movie_actor) ;
        movie_actor_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec1 = QueryExecutionFactory.create(movie_actor_query, infmodel) ;
        ResultSet movie_actor_query_rs= qexec1.execSelect() ;
        QuerySolution sol1 = movie_actor_query_rs.nextSolution();
        RDFNode actor_individual = sol1.get("x") ;
        
        String movie_award = jComboBox3.getSelectedItem().toString().replaceAll(" ", "_");
        String movie_director = jComboBox4.getSelectedItem().toString();
        String director = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
        + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
        + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
        + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
        + "\nSELECT ?x "
        + "\nWHERE { "
        + "\n?x rdf:type movies:Director ; "
        + "\nmovies:name ?name ;"
        + "\nFILTER(?name = '%s') \n}" , movie_director);   
        Query director_query = QueryFactory.create(director) ;
        director_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec2 = QueryExecutionFactory.create(director_query, infmodel) ;
        ResultSet director_query_rs= qexec2.execSelect() ;
        QuerySolution sol2 = director_query_rs.nextSolution();
        RDFNode director_individual = sol2.get("x") ;
        
        String movie_distributor = jComboBox5.getSelectedItem().toString().replaceAll(" ", "_");
        
        String movie_writter = jComboBox6.getSelectedItem().toString();
        String writer = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
        + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
        + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
        + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
        + "\nSELECT ?x "
        + "\nWHERE { "
        + "\n?x rdf:type movies:Writer ; "
        + "\nmovies:name ?name ;"
        + "\nFILTER(?name = '%s') \n}" , movie_writter);   
        Query writer_query = QueryFactory.create(writer) ;
        writer_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec3 = QueryExecutionFactory.create(writer_query, infmodel) ;
        ResultSet writer_query_rs= qexec3.execSelect() ;
        QuerySolution sol3 = writer_query_rs.nextSolution();
        RDFNode writter_individual = sol3.get("x") ;        
        
        String movie_composer = jComboBox7.getSelectedItem().toString();
        String composer = String.format("PREFIX owl: <http://www.w3.org/2002/07/owl#> "
        + "\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
        + "\nPREFIX rdfs: <http://www.w3.org/2000/01/22-rdf-schema#> "
        + "\nPREFIX movies: <http://www.semanticweb.org/evans/ontologies/movies#> "
        + "\nSELECT ?x "
        + "\nWHERE { "
        + "\n?x rdf:type movies:Composer ; "
        + "\nmovies:name ?name ;"
        + "\nFILTER(?name = '%s') \n}" , movie_composer);   
        Query composer_query = QueryFactory.create(composer) ;
        composer_query.serialize(new IndentedWriter(System.out,true)) ;
        QueryExecution qexec4 = QueryExecutionFactory.create(composer_query, infmodel) ;
        ResultSet composer_query_rs= qexec4.execSelect() ;
        QuerySolution sol4 = composer_query_rs.nextSolution();
        RDFNode composer_individual = sol4.get("x") ; 
        
        String movie_language = jComboBox8.getSelectedItem().toString(); 
        System.out.print(movie_individual.toString());
        try{
            
            Individual i = infmodel.getIndividual(movie_individual.toString());
            i.addProperty(infmodel.getProperty("http://www.semanticweb.org/evans/ontologies/movies#features_actor"), infmodel.createObjectProperty(actor_individual.toString()));
            if (movie_award != "None"){
            i.addProperty(infmodel.getProperty("http://www.semanticweb.org/evans/ontologies/movies#winner_of"), infmodel.createObjectProperty("http://www.semanticweb.org/evans/ontologies/movies#"+movie_award));
            }
            i.addProperty(infmodel.getProperty("http://www.semanticweb.org/evans/ontologies/movies#directed_by"), infmodel.createObjectProperty(director_individual.toString()));
            i.addProperty(infmodel.getProperty("http://www.semanticweb.org/evans/ontologies/movies#distributed_by"), infmodel.createObjectProperty("http://www.semanticweb.org/evans/ontologies/movies#"+movie_distributor));
            i.addProperty(infmodel.getProperty("http://www.semanticweb.org/evans/ontologies/movies#has_language"), infmodel.createObjectProperty("http://www.semanticweb.org/evans/ontologies/movies#"+movie_language));

            /// add script writter to movie
            Individual i2 = infmodel.getIndividual(movie_individual.toString()+"_script");
            i2.addProperty(infmodel.getObjectProperty("http://www.semanticweb.org/evans/ontologies/movies#written_by"),infmodel.createObjectProperty(writter_individual.toString()));
           
            // add soundtrack composer to movie
            Individual i3 = infmodel.getIndividual(movie_individual.toString()+"_OST");
            i3.addProperty(infmodel.getObjectProperty("http://www.semanticweb.org/evans/ontologies/movies#composed_by"),infmodel.createObjectProperty(composer_individual.toString()));
            
            // write to owl document
            infmodel.write( new FileOutputStream("movies_onto.owl") ,""); 
        
        }catch (NoWriterForLangException e){
        System.out.println("Invalid format option selected!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(add_new.class.getName()).log(Level.SEVERE, null, ex);
        }
        


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
 
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(edit_movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(edit_movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(edit_movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(edit_movie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new edit_movie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
