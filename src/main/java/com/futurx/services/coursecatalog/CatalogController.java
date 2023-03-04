package com.futurx.services.coursecatalog;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.sun.source.tree.InstanceOfTree;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CatalogController {

   private EurekaClient client;

    @RequestMapping("/")
    public String getCatalogHome() {
        String courseAppMessage = "";
        //String courseAppURL = "http://localhost:8080";
        InstanceInfo instanceInfo = client.getNextServerFromEureka("fx-course-service", false);
        RestTemplate restTemplate = new RestTemplate();
        String courseAppURL = instanceInfo.getHomePageUrl();
       courseAppMessage = restTemplate.getForObject(courseAppURL, String.class);

        return ("Welcome to FutureX Course Catalog " + courseAppMessage);
    }

    @RequestMapping("/catalog")
    public String getCatalog() {
        String courses = "";
        //String courseAppURL = "http://localhost:8080/courses";
        InstanceInfo instanceInfo = client.getNextServerFromEureka("fx-course-service", false);
        RestTemplate restTemplate = new RestTemplate();
        String courseAppURL = instanceInfo.getHomePageUrl() + "/courses";
        courses = restTemplate.getForObject(courseAppURL, String.class);

        return ("Our courses are " + courses);
    }

    @RequestMapping("/firstcourse")
    public String getSpecificCourse() {
        Course course = new Course();
        //String courseAppURL = "http://localhost:8080/1";
        InstanceInfo instanceInfo = client.getNextServerFromEureka("fx-course-service", false);
        String courseAppURL = instanceInfo.getHomePageUrl();
        courseAppURL = courseAppURL + "/1";
        RestTemplate restTemplate = new RestTemplate();
        course = restTemplate.getForObject(courseAppURL, Course.class);

        assert course != null;
        return ("Our first course is " + course.getCoursename());
    }
}
