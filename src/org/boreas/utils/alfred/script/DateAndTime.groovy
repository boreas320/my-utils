package org.boreas.utils.alfred.script
/**
 * Created by shuai.xiang@renren-inc.com on 16/3/21.
 */



import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by shuai.xiang@renren-inc.com on 16/1/20.
 */
public class XmlGenerator {
//    public static void main(String[] args) throws IOException {
//        String arg = args.length > 0 ? args[0] : "";
//        Items ret = new Items();
//        List<Item> items = csvReader("project_list.csv");
//        items.forEach(item -> {
//            if (item.getTitle().startsWith(arg)) {
//                ret.addItem(item);
//            }
//        });
//
//
//        System.out.println(ret);
//    }

    static Document document;

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = builder.newDocument();
    }

    /**
     * <item uid="desktop" arg="~/Desktop" valid="YES" autocomplete="Desktop" type="file">
     * <title>Desktop</title>
     * <subtitle>~/Desktop</subtitle>
     * <icon type="fileicon">~/Desktop</icon>
     * </item>
     */
    static class Item {
        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getAutocomplete() {
            return autocomplete;
        }

        public void setAutocomplete(String autocomplete) {
            this.autocomplete = autocomplete;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconType() {
            return iconType;
        }

        public void setIconType(String iconType) {
            this.iconType = iconType;
        }

        private String uid;
        private String arg;
        private boolean valid = true;
        private String autocomplete;
        private String type;
        private String title;
        private String subtitle;
        private String icon;
        private String iconType;


        public Item(String uid, String arg, boolean valid, String autocomplete, String type, String title, String subtitle, String icon, String iconType) {
            this.uid = uid;
            this.arg = arg;
            this.valid = valid;
            this.autocomplete = autocomplete;
            this.type = type;
            this.title = title;
            this.subtitle = subtitle;
            this.icon = icon;
            this.iconType = iconType;
        }

        public Element toElement() {

            Element item = document.createElement("item");
            item.setAttribute("uid", uid);
            item.setAttribute("arg", arg);
            item.setAttribute("valid", valid ? "yes" : "no");
            item.setAttribute("autocomplete", autocomplete);
            item.setAttribute("type", type);


            Element titleElement = document.createElement("title");
            titleElement.setTextContent(title);

            item.appendChild(titleElement);
            Element subtitleElement = document.createElement("subtitle");
            subtitleElement.setTextContent(subtitle);


            item.appendChild(subtitleElement);
            Element iconElement = document.createElement("icon");
            iconElement.setAttribute("type", iconType);
            iconElement.setTextContent(icon);
            item.appendChild(iconElement);
            return item;
        }


    }

    static class Items {

        private List<Item> itemList = new ArrayList<>();


        public void addItem(Item item) {
            itemList.add(item);
        }

        public String out(Document document) throws TransformerException {
            TransformerFactory tf = TransformerFactory.newInstance();

            Transformer transformer = null;
            try {
                transformer = tf.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult(stringWriter);
            transformer.transform(source, result);
            return stringWriter.toString();


        }

        @Override
        public String toString() {

            Element items = document.createElement("items");
            document.appendChild(items);
//below is java 8 lambda syntax
//            itemList.forEach(item -> items.appendChild(item.toElement()));
            itemList.each { item -> items.appendChild(item.toElement()) }
            try {
                return out(document);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

if (args.length != 1) {
    errorItem = new XmlGenerator.Item("", "error args", true, "", "", "", "", "", "")
    items = new XmlGenerator.Items()
    items.addItem(errorItem)
    println items.toString()

}