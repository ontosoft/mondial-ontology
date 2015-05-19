package onto1;

import it.unibz.krdb.obda.io.ModelIOManager;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.owlrefplatform.core.QuestConstants;
import it.unibz.krdb.obda.owlrefplatform.core.QuestPreferences;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWL;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLConnection;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLFactory;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLResultSet;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLStatement;

import java.io.File;
import java.util.ArrayList;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

public class QuestOWLE {
		/*
		 
		 * 
		 */
		final String owlfile = "src/main/resources/onto1/mondial.owl";
		final String obdafile = "src/main/resources/onto1/mondial.obda";
		private String sparqlQuery = "";
		private ArrayList<ArrayList<String>> queryResult = new ArrayList<ArrayList<String>>();

		public QuestOWLE(String sparqlQuery2) {
			this.sparqlQuery = sparqlQuery2;

		}

		public String getSparqlQuery() {
			return sparqlQuery;
		}

		public void setSparqlQuery(String sparqlQuery) {
			this.sparqlQuery = sparqlQuery;
		}

		public ArrayList<ArrayList<String>> getQueryResult() {
			return queryResult;
		}

		public void setQueryResult(ArrayList<ArrayList<String>> queryResult) {
			this.queryResult = queryResult;
		}
		//Trimming " and "^^xsd:string
		public String trimmString(String s){
			int last = s.lastIndexOf("\"^^");
			
			return s.substring(1, last);
			
		}

		public void runQuery() throws Exception {

			/*
			 * Load the ontology from an external .owl file.
			 */
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology = manager.loadOntologyFromOntologyDocument(new File(owlfile));

			/*
			 * Load the OBDA model from an external .obda file
			 */
			OBDADataFactory fac = OBDADataFactoryImpl.getInstance();
			OBDAModel obdaModel = fac.getOBDAModel();
			ModelIOManager ioManager = new ModelIOManager(obdaModel);
			ioManager.load(obdafile);

			/*
			 * Prepare the configuration for the Quest instance. The example below shows the setup for
			 * "Virtual ABox" mode
			 */
			QuestPreferences preference = new QuestPreferences();
			preference.setCurrentValueOf(QuestPreferences.ABOX_MODE, QuestConstants.VIRTUAL);

			/*
			 * Create the instance of Quest OWL reasoner.
			 */
			QuestOWLFactory factory = new QuestOWLFactory();
			factory.setOBDAController(obdaModel);
			factory.setPreferenceHolder(preference);
			QuestOWL reasoner = factory.createReasoner(ontology, new SimpleConfiguration());

			/*
			 * Prepare the data connection for querying.
			 */
			QuestOWLConnection conn = reasoner.getConnection();
			QuestOWLStatement st = conn.createStatement();

			/*
			 * Get the book information that is stored in the database
			 */
		

			try {
				QuestOWLResultSet rs = st.executeTuple(sparqlQuery);
				int columnSize = rs.getColumnCount();
				//Adding to the list of objects which represent query resulsts
								
				while (rs.nextRow()) {
					ArrayList<String> queryRow = new ArrayList<String>();
					for (int idx = 1; idx <= columnSize; idx++) {
						OWLObject binding = rs.getOWLObject(idx);
						System.out.print(binding.toString() + ", ");
						queryRow.add(trimmString(binding.toString()));
						
					}
					this.queryResult.add(queryRow);
					System.out.print("\n");
				}
				rs.close();

				/*
				 * Print the query summary
				 */
				QuestOWLStatement qst = (QuestOWLStatement) st;
				String sqlQuery = qst.getUnfolding(sparqlQuery);

				System.out.println();
				System.out.println("The input SPARQL query:");
				System.out.println("=======================");
				System.out.println(sparqlQuery);
				System.out.println();

				System.out.println("The output SQL query:");
				System.out.println("=====================");
				System.out.println(sqlQuery);

			} finally {			
				/*
				 * Close connection and resources
				 */
				st.close();

				conn.close();

				reasoner.dispose();
			}
		}
		
}
