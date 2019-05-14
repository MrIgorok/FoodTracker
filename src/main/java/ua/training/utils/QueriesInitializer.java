package ua.training.utils;

import java.lang.reflect.Field;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public abstract class QueriesInitializer {
    private String folder = "queries";

    public QueriesInitializer() {
        if (loaded()) return;

        synchronized (QueriesInitializer.class) {
            if (loaded()) return;

            Class<?> initializingClass = this.getClass();
            ResourceBundle resourceBundle = PropertyResourceBundle.getBundle(folder + '.' + initializingClass.getName().toLowerCase());

            try {

                for (Field field : initializingClass.getDeclaredFields()) {
                    field.set(this, resourceBundle.getString(field.getName()));
                }

            } catch (Exception e) {
                // TODO: log
                throw new RuntimeException(e);
            }

            setLoad();
        }
    }

    protected abstract boolean loaded();
    protected abstract void setLoad();

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder.replace('/', '.').replace('\\', '.');
    }
}
