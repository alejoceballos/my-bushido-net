package com.momo2x.mbdn.members.constant;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import static java.util.Calendar.DECEMBER;

public interface DefaultValueConstants {
    UUID ADMIN_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    String ADMIN_NICKNAME = "admin";
    String ADMIN_NAME = "Administrator";
    Date ADMIN_BIRTH_DATE = new GregorianCalendar(2022, DECEMBER, 21).getTime();
    String AVATAR_FILE = "classpath:db/migration/silhouette-3x4.png";
    String AVATAR_DESCRIPTION = "silhouette";
}
