# Database synchronizer

`dbSync` transfers changes in card data from Card Suite CMS and RTPS system databases to LinkApp database.
In addition, `dbSync` handles new card blocking for delivery, creation of Priority Pass cards, etc.

More info on `dbSync` can be found in [Confluence](https://confluence.luminorgroup.com/pages/viewpage.action?spaceKey=CARD&title=Database+synchronizer
)

### Command to run `dbSync` locally

Template:
```shell
java -Dlogging.config=/PATH_TOFILE/log4j_example.xml -Dlog4j.configuration=/PATH_TOFILE/log4j_example.xml -jar dbSync-2.0.0-SNAPSHOT.jar
```

Example:
```shell
java -Dlogging.config=file:/CODE/LinkApp/db-synchronizer/src/main/resources/log4j_example.xml -Dlog4j.configuration=file:/CODE/LinkApp/db-synchronizer/src/main/resources/log4j_example.xml -jar dbSync-2.0.0-SNAPSHOT.jar
```

### What was done 12.20.2021

This is hte first SpringBoot version. 
Previous version was pure java without Application server and spring.
Also `dbSync` for LV and LT was in different branches, and had code duplication about 80%.
So, what was done :
* Merged LV and LT code together.
* Cleaned some unused code.
* Removed all (there still some) code duplications.
* Migrated to SpringBoot, Making it work with 3 BD
* Improved architecture of the application (but still not good) 
* Making work Flyway
* Making work "old" code (DAO) with transactions
* Making work new code with JPA and EntityManager
* Making LOG working how it was done in previous version

Application become much better than it was before. But still need to be improved.

### What need TODO 12.20.2021
* Upgrade SpringBoot latest
* Redo LOG -- need to understand why where are different loggers and rewrite
* Need to re-analyze and refactor transactions, move them to `service` layer
* Make application running 24/7 and create `cron` expressions to run CMS, RTPS synchronization.
  Create `cron` expressions for running `cleaning` DB. Make DB `lock` for avoid running same processes in parallel.
* Remove all deprecated method/classes calls 
* Improve architecture in general.

### PS. 
Please not be too strict when `reading` the code, I had a lot of plans to improve much thinks in there, 
but really had no time to do that. I did this in `free` time and in scope of some tasks.
In total this SpringBoot version have more than 100 commits, and all files was changed :)

###  Useful links
* External log4J configuration - [stackoverflow link](https://stackoverflow.com/questions/32078317/how-do-i-use-an-external-log4j-xml-configuration-file-using-spring-boot/37748999)
* Spring JPA – Multiple Databases  - [baeldung link](https://www.baeldung.com/spring-data-jpa-multiple-databases)
* Database Migrations with Flyway - [baeldung link](https://www.baeldung.com/database-migrations-with-flyway)
