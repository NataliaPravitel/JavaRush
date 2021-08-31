package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerStrategy implements Strategy {
  private static final String URL_FORMAT = "https://career.habr.com/vacancies?page=%d&q=java%s&type=all"; //RIGHT URL for habr 27/08/2021
                                          //"https://career.habr.com/vacancies?q=java+%s&page=%d";  //ERROR URL!! for validator
  @Override
  public List<Vacancy> getVacancies(String searchString) {
    List<Vacancy> vacancies = new ArrayList<>();
    int page = 1;                                                                               //RIGHT PAGE for habr 27/08/2021
              //0;                                                                                  //ERROR PAGE for validator
    boolean hasVacancies = true;
    try {
      do {
        Document document = getDocument(searchString, page);
        document.html();

        Elements vacancyElements = document
                .getElementsByClass("vacancy-card__info");                                      //RIGHT CLASS for habr 27/08/2021
//                .getElementsByClass("job");                                             //ERROR CLASS for validator

        if (!vacancyElements.isEmpty()) {
          vacancyElements.forEach(vacancyElement -> {
            //ERROR CODE for validator
//                    Elements title = vacancyElement.getElementsByClass("title");          //ERROR CLASS for validator
//                    Elements links = title.get(0).getElementsByTag("a");
//                    Elements locations = vacancyElement.getElementsByClass("location");   //ERROR CLASS for validator
//                    Elements companyName = vacancyElement.getElementsByClass("company_name");//ERROR CLASS for validator
//                    Elements salary = vacancyElement.getElementsByClass("count");         //ERROR CLASS for validator
//
//                    Vacancy vacancy = new Vacancy();
//                    vacancy.setSiteName("career.habr.com");
//                    vacancy.setTitle(links.get(0).text());
//                    vacancy.setUrl("https://career.habr.com" + links.get(0).attr("href"));
//                    vacancy.setCity(locations.size() > 0 ? locations.get(0).text() : "");
//                    vacancy.setCompanyName(companyName.get(0).text());
//                    vacancy.setSalary(salary.size() > 0 ? salary.get(0).text() : "");
//                    vacancies.add(vacancy);

                    //MY RIGHT CODE FOR HABR 27/08/2021
            Element link = vacancyElement.getElementsByClass("vacancy-card__title-link")
                    .get(0);
            Element location = vacancyElement.getElementsByClass("vacancy-card__meta")
                    .get(0);
            String city = location
                    .getElementsByClass("link-comp link-comp--appearance-dark").text();
            if (city.isEmpty()) {
              city = location.getElementsByClass("preserve-line").text()
                      .replace(city, "")
                      .replace("Полный рабочий день","").trim();
            }
            String salary = vacancyElement.getElementsByClass("vacancy-card__salary").text();
            salary = !salary.isEmpty() ? salary : "";
            Vacancy vacancy = new Vacancy();
            vacancy.setTitle(link.text());
            vacancy.setSalary(salary);
            vacancy.setCity(city);
            vacancy.setCompanyName(vacancyElement
                    .getElementsByClass("vacancy-card__company-title").text());
            vacancy.setSiteName("https://career.habr.com");
            vacancy.setUrl("https://career.habr.com" + link.attr("href"));
            vacancies.add(vacancy);
          }
          );
        } else {
          hasVacancies = false;
        }
        //hasVacancies = false;/////////////////////////////////////////////////////////////////////FOR JAVARUSH TEST FILE
        page++;
      } while (hasVacancies);
      System.out.println("HABR.COM: " + vacancies.size());

    } catch (IOException e) {
    }
    return vacancies;
  }

  protected Document getDocument(String searchString, int page) throws IOException {
    return Jsoup.connect(
            String.format(URL_FORMAT, page, "%20" + searchString)                               //Right format url for habr 27/08/2021
//            String.format(URL_FORMAT, searchString, page)                                         //ERROR FORMAT URL for validator
                    //"https://javarush.ru/testdata/big28data2.html"////////////////////////////////JAVARUSH TEST FILE
            )
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36")
            .referrer("")
            .get();
  }
}
