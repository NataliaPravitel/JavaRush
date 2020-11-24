package com.javarush.task.task27.task2712.ad;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

//Ресторан(17)
//Реализуем третий и четвертый пункт статистики - список активных и неактивных роликов
//Для этого проще использовать доступ к хранилищу рекламных роликов - класс AdvertisementStorage.
//1. В пакете ad создай StatisticAdvertisementManager, который будет предоставлять информацию из AdvertisementStorage в нужном нам виде.
//Сделай его синглтоном.
//
//2. В классе StatisticAdvertisementManager создайте и проинициализируйте поле типа AdvertisementStorage.
//
//3. В StatisticAdvertisementManager создай два (или один) метода (придумать самостоятельно), которые из хранилища AdvertisementStorage достанут все необходимые данные - соответственно список активных и неактивных рекламных роликов.
//Активным роликом считается тот, у которого есть минимум один доступный показ.
//Неактивным роликом считается тот, у которого количество показов равно 0.
//
//4. Реализуй логику методов printActiveVideoSet и printArchivedVideoSet в классе DirectorTablet.
//Используй методы/метод, созданные в предыдущем пункте.
//Сортировать по имени видео-ролика в алфавитном порядке
//Сначала английские, потом русские.
//
//Пример вывода для printActiveVideoSet:
//First Video - 100
//Second video - 10
//Third Video - 2
//четвертое видео - 4
//
//Через 50 показов пример вывода для printArchivedVideoSet:
//Second video
//Third Video
//четвертое видео
//
//
//Требования:
//1. Метод printActiveVideoSet в классе DirectorTablet должен быть реализован в соответствии с условием задачи.
//2. Метод printArchivedVideoSet в классе DirectorTablet должен быть реализован в соответствии с условием задачи.
//3. Класс StatisticAdvertisementManager должен быть синглтоном.
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static synchronized StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }

    public TreeSet<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> videos = storage.list();
        Comparator<Advertisement> comparator = new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };

        TreeSet<Advertisement> activeVideoSet = new TreeSet<>(comparator);
        TreeSet<Advertisement> archiveVideoSet = new TreeSet<>(comparator);

        for (Advertisement adv : videos) {
            if (adv.isActive()) {
                activeVideoSet.add(adv);
            } else {
                archiveVideoSet.add(adv);
            }
        }

        return isActive ? activeVideoSet : archiveVideoSet;
    }
}