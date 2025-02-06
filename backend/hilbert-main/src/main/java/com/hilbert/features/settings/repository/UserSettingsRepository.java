package com.hilbert.features.settings.repository;

import com.hilbert.features.settings.model.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

}
