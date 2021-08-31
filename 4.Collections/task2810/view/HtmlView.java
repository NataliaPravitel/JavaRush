package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class HtmlView implements View {
  private Controller controller;
  private final String filePath = "./4.JavaCollections/src/" +
          this.getClass().getPackage().getName().replaceAll("\\.", "/") +
          "/vacancies.html";

  @Override
  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void userCitySelectEmulationMethod() {
    controller.onCitySelect("Minsk");
  }

  @Override
  public void update(List<Vacancy> vacancies) {
    try {
//      if (Files.notExists(Paths.get(filePath))) {
//        Files.createFile(Paths.get(filePath));
//      }
      updateFile(getUpdatedFileContent(vacancies));
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(vacancies.size());
  }

  private String getUpdatedFileContent(List<Vacancy> vacancies) {
    vacancies.sort(Comparator.comparing(Vacancy::getCity));
    try {
      Document document = getDocument();
      Elements templates = document.getElementsByClass("template");
      Elements templatesCopy = templates.clone();
      //Получи первый элемент и используй его в качестве шаблона для добавления новой строки в таблицу вакансий.
      Element template = templatesCopy.removeAttr("style").removeClass("template").get(0);

      Elements previousElements = document.getElementsByClass("vacancy");
      previousElements.forEach(element -> {
        if (!element.hasClass("template")) {
          element.remove();
        }
      });

      vacancies.forEach(vacancy -> {
        Element templateClone = template.clone();
        Element href = templateClone.getElementsByAttribute("href").get(0);
        href.appendText(vacancy.getTitle());
        href.attr("href", vacancy.getUrl());
        templateClone.getElementsByClass("city").get(0).appendText(vacancy.getCity());
        templateClone.getElementsByClass("companyName").get(0)
                .appendText(vacancy.getCompanyName());
        templateClone.getElementsByClass("salary").get(0).appendText(vacancy.getSalary());
        //4.6. добавь outerHtml элемента, в который ты записывал данные вакансии,
        //непосредственно перед шаблоном <tr class="vacancy template" style="display: none">
        templates.before(templateClone.outerHtml());
      });
      return document.html();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Some exception occurred";
  }

  private void updateFile(String content) {
    File file = new File(filePath);
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(content.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected Document getDocument() throws IOException {
    return Jsoup.parse(new File(filePath), "UTF-8");
  }

}
