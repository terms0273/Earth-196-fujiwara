/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import com.avaje.ebean.Ebean;
import java.io.IOException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.test.FakeApplication;
import static play.test.Helpers.*;

/**
 *
 * @author y-fujiwara
 */
public class FakeApp {
    public static FakeApplication app;
    public static String createDb1 = "";
    public static String dropDb1 = "";
    
    @BeforeClass
    public static void startApp() throws IOException {
        app = fakeApplication(inMemoryDatabase());
        start(app);
        
        String evolutionContent;
        evolutionContent = FileUtils.readFileToString(app.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));
        String[] splitEvolutionContent = evolutionContent.split("# --- !Ups");
        String[] upsDowns = splitEvolutionContent[1].split("# --- !Downs");
        
        createDb1 = upsDowns[0];
        dropDb1 = upsDowns[1];    
    }
    
    @Before
    public void createCleanDb() {
        initDb();
    }
    
    public static void initDb() {
        Ebean.execute(Ebean.createCallableSql(dropDb1));
        Ebean.execute(Ebean.createCallableSql(createDb1));
        
        // Ebeanキャッシュのクリア
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache("play");
        cache.removeAll();
    }
    
    public static void restartAPP() {
        start(app);
    }
    
    @AfterClass 
    public static void stopApp() {
        stop(app);
    }
    
}
