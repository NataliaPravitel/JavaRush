package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
  private static final String URL_FORMAT =
          "https://hh.ru/search/vacancy?text=java+junior+%s&items_on_page=20&page=%d";
          //"https://hh.ru/search/vacancy?text=java+%s&page=%d";                                    //ERROR for validator

  @Override
  public List<Vacancy> getVacancies(String searchString) {
    List<Vacancy> vacancies = new ArrayList<>();
    int page = 0;
    boolean hasVacancies = true;
    try {
      do {
        Document document = getDocument(searchString, page);
        document.html();

        Elements vacancyElements = document
                .getElementsByClass("vacancy-serp-item");

        if (!vacancyElements.isEmpty()) {
          vacancyElements.forEach(vacancyElement -> {
            Elements links = vacancyElement
                    .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title");

            Elements locations = vacancyElement
                    .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address");
            Elements employers = vacancyElement
                    .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer");
            Elements compensation = vacancyElement
                    .getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");

            Vacancy vacancy = new Vacancy();
            vacancy.setTitle(links.text());
            vacancy.setSalary(!compensation.isEmpty() ? compensation.text() : "");
            vacancy.setCity(locations.text());
            vacancy.setCompanyName(employers.text());
            vacancy.setUrl(links.attr("href"));
            vacancy.setSiteName("https://hh.ru");
            vacancies.add(vacancy);
          }
          );
        } else {
          hasVacancies = false;
        }
        //hasVacancies = false;/////////////////////////////////////////////////////////////////////FOR JAVARUSH TEST FILE
        page++;
      } while (hasVacancies);
      System.out.println("HH.RU: " + vacancies.size());

    } catch (IOException e) {
    }
    return vacancies;
  }

  protected Document getDocument(String searchString, int page) throws IOException {
    return Jsoup.connect(
            String.format(URL_FORMAT, searchString, page)
            //"https://javarush.ru/testdata/big28data.html"/////////////////////////////////////////JAVARUSH TEST FILE
            )
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36")
            .referrer("")
            .get();
  }

}
