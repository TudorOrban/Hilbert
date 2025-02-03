package com.hilbert.core.user.repository;

import com.hilbert.core.user.model.User;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.UserSearchParams;

public interface UserSearchRepository {

    PaginatedResults<User> searchUsers(UserSearchParams searchParams);
}
