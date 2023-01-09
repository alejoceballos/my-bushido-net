package com.momo2x.mbdn.contacts.constant;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static java.util.Calendar.DECEMBER;

public interface DefaultValueConstants {
    UUID ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    String ADMIN_FIRST_NAME = "Administrator";
    String ADMIN_MIDDLE_NAME = "Of The";
    String ADMIN_LAST_NAME = "System";
    Date ADMIN_BIRTH_DATE = new GregorianCalendar(2022, DECEMBER, 21).getTime();
    String ADMIN_PHOTO_FILE = "classpath:db/migration/silhouette-3x4.png";
}
