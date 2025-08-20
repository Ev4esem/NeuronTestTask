package com.dagteam.neurontesttask.domain

import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import java.util.SortedMap
import javax.inject.Inject

class GetMyBuysUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(): SortedMap<String, List<Purchase>> {
        return repository.getMyBuys().groupBy { it.date }
            .toSortedMap(compareByDescending { it })
    }
}