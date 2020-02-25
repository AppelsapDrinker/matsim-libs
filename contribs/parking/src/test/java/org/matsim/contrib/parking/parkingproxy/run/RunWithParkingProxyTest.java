package org.matsim.contrib.parking.parkingproxy.run;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;
import org.matsim.testcases.MatsimTestUtils;
import org.matsim.utils.eventsfilecomparison.EventsFileComparator.Result;

import static org.junit.Assert.fail;

public class RunWithParkingProxyTest{
        private static final Logger log = Logger.getLogger( RunWithParkingProxyTest.class ) ;
        @Rule public MatsimTestUtils utils = new MatsimTestUtils();

        @Test
        public void testMain(){

                try{
                        RunWithParkingProxy.main( new String []{ IOUtils.extendUrl( ExamplesUtils.getTestScenarioURL( "chessboard" ), "config.xml" ).toString()
                                        , "--config:controler.outputDirectory=" + utils.getOutputDirectory()
                                        , "--config:controler.lastIteration=2"
                                        , "--config:controler.writePlansInterval=1"
                                        , "--config:parkingProxy.method=events"
                        } );
                        {
                                String expected = utils.getInputDirectory() + "/output_events.xml.gz" ;
                                String actual = utils.getOutputDirectory() + "/output_events.xml.gz" ;
                                Result result = EventsUtils.compareEventsFiles( expected, actual );
                                if(!result.equals(Result.FILES_ARE_EQUAL)) {
                                	throw new RuntimeException("Events comparison ended with result " + result.name());
                                }
                        }
                        {
                                final Population expected = PopulationUtils.createPopulation( ConfigUtils.createConfig() );
                                PopulationUtils.readPopulation( expected, utils.getInputDirectory() + "/output_plans.xml.gz" );
                                final Population actual = PopulationUtils.createPopulation( ConfigUtils.createConfig() );
                                PopulationUtils.readPopulation( actual, utils.getOutputDirectory() + "/output_plans.xml.gz" );
                                if(!PopulationUtils.comparePopulations( expected, actual )) {
                                	throw new RuntimeException("Plans file comparison ended with result false");
                                }
                        }


                } catch ( Exception ee ) {
                        log.fatal(ee) ;
                        fail() ;
                }



        }
}
