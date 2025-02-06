package com.hilbert.features.settings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingsData {

    private Boolean notificationsOn;
    private Boolean profilePublic;
    private Boolean darkMode;
}
