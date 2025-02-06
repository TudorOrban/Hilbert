package com.hilbert.features.settings.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hilbert.shared.error.types.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "settings", columnDefinition = "TEXT", nullable = false)
    private String settingsJson;

    @Transient
    private UserSettingsData settings;

    public UserSettingsData getSettings() {
        if (this.settings != null || this.settingsJson == null) {
            return this.settings;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            this.settings = mapper.readValue(settingsJson, UserSettingsData.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid User Settings Data: " + e);
        }

        return this.settings;
    }

    public void setSettings(UserSettingsData settings) {
        this.settings = settings;

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            this.settingsJson = mapper.writeValueAsString(settings);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid User Settings Data: " + e);
        }
    }
}
