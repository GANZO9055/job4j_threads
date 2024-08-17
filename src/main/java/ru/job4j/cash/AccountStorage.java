package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;

        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);

        if (fromAccount.isPresent()
                && toAccount.isPresent()
                && fromAccount.get().amount() >= amount) {

            Account updateFromAccount = new Account(fromAccount.get().id(),
                    fromAccount.get().amount() - amount);
            Account updateToAccount = new Account(toAccount.get().id(),
                    toAccount.get().amount() + amount);

            accounts.put(updateFromAccount.id(), updateFromAccount);
            accounts.put(updateToAccount.id(), updateToAccount);

            result = true;
        }
        return result;
    }
}
