package com.javarush.task.task33.task3309;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* 
Комментарий внутри xml
Реализовать метод toXmlWithComment, который должен возвращать строку - xml представление объекта obj.
В строке перед каждым тегом tagName должен быть вставлен комментарий comment.
Сериализация obj в xml может содержать CDATA с искомым тегом. Перед ним вставлять комментарий не нужно.

Пример вызова:
toXmlWithComment(firstSecondObject, "second", "it's a comment")

Пример результата:
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<first>
<!--it's a comment-->
<second>some string</second>
<!--it's a comment-->
<second>some string</second>
<!--it's a comment-->
<second><![CDATA[need CDATA because of < and >]]></second>
<!--it's a comment-->
<second/>
</first>


Requirements:
1. Метод toXmlWithComment должен быть статическим.
2. Метод toXmlWithComment должен быть публичным.
3. Если во входящем xml отсутствует искомый тег, то добавлять комментарии не нужно.
4. Количество комментариев вставленных в xml должно быть равно количеству тегов tagName.
5. Метод toXmlWithComment должен возвращать xml в виде строки преобразованной в соответствии с условием задачи.




1. Создайте DocumentBuilderFactory, который позволит получить анализатор, создающий DOM из xml,
выставите setCoalescing(true) - это нужно, чтобы узлы CDATA преобразовались в текст и добавлялись к смежному текстовому узлу.
2. С его помощью создайте DocumentBuilder, который определяет интерфейс для получения DOM из xml.
3. С его помощью создайте Document.
4. Далее создайте контекст и маршаллер и с помощью второго сериализуйте ваш объект в полученный выше документ.
marshaller.marshal(obj, document)

5. Получите из обновлённого документа список искомых тегов (NodeList) при помощи метода getElementsByTagName(tagName).
6. Пройдитесь по полученному списку NodeList и обработайте каждый узел таким образом:
6.1. Добавьте комментарий перед полученным узлом. Для этого надо получить родительский узел (getParentNode()),
вызвать у него метод insertBefore, который позволит вставить что-то перед вашим узлом,
в этот метод передайте комментарий, созданный при помощи документа (document.createComment(comment)) и ваш узел.
Получается:
node.getParentNode().insertBefore(document.createComment(comment), node);

6.2. Теперь после комментария надо добавить переход на новую строку. Для этого тоже используйте insertBefore,
как выше, только первым параметром передайте созданный при помощи Document текстовый узел "\n". Вот так:
node.getParentNode().insertBefore(document.createTextNode("\n"), node);

7. Создайте TransformerFactory, который позволит вам создать Transformer.
8. С его помощью создайте Transformer, который позволит получить из DOM результат (в нашем случае в виде текста).
8.1. Установите для Transformer setOutputProperty(OutputKeys.STANDALONE, "no").
Этот метод нужен для установки выходного свойства, которое будет действовать при преобразовании дерева.
Константа OutputKeys.STANDALONE означает, будет ли выводиться отдельная декларация документа (хз, что это), мы ставит "no".
9. Для трансформации документа вызовите у Transformer метод transform, который трансформирует xml в то,
что нам нужно. Первый параметром передайте новый объект DOMSource (я так и не понял, для чего он нужен.
Он имеет в себе исходный DOM для преобразования, но хз зачем), при создании которого используйте document,
вторым параметром передайте StreamResult (содержит в себе результат преобразования),
в конструктор которого передайте новый StringWriter. Всё это выглядит вот так:
transformer.transform(new DOMSource(document), new StreamResult(writer));

10. Верните StringWriter.toString(). 😁

*/

public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment)
            throws JAXBException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();          //1
        builderFactory.setCoalescing(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();                         //2
        Document document = builder.newDocument();                                             //3

        JAXBContext context = JAXBContext.newInstance(obj.getClass());                         //4
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, document);

        document.normalizeDocument();                                                          //5

        NodeList nodeList = document.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {                                       //6
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeName().equals(tagName)) {
                currentNode.getParentNode().insertBefore(document.createComment(comment),      //6.1
                        currentNode);
                currentNode.getParentNode().insertBefore(document.createTextNode("\n"),  //6.2
                        currentNode);
            }
        }

        TransformerFactory factory = TransformerFactory.newInstance();                         //7
        Transformer transformer = factory.newTransformer();                                    //8
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");                      //8.1
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));              //9

        return writer.toString();                                                              //10
    }

    public static void main(String[] args) {

    }
}
