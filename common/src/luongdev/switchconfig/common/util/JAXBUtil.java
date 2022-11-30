package luongdev.switchconfig.common.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class JAXBUtil {
    private JAXBUtil() {
    }

    @SuppressWarnings("all")
    public static <T> T unmarshallXML(String content, Class<T> clazz) throws JAXBException {
        var context = JAXBContext.newInstance(clazz);
        var unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new StringReader(content));
    }

    public static <T> String marshallObject(T instance, boolean format) throws JAXBException {
        var context = JAXBContext.newInstance(instance.getClass());
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, format);

        try (var os = new ByteArrayOutputStream()) {
            marshaller.marshal(instance, os);

            return os.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new JAXBException(e);
        }
    }

    public static <T> String marshallObject(T instance) throws JAXBException {
        return marshallObject(instance, true);
    }
}