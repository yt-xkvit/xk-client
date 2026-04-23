package com.xkclient.service;

import com.xkclient.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing Minecraft accounts
 */
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final ConfigService configService;
    private final List<Account> accounts;

    public AccountService(ConfigService configService) {
        this.configService = configService;
        this.accounts = configService.loadAccounts();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        save();
        logger.info("Added account: {}", account.getUsername());
    }

    public void removeAccount(String username) {
        accounts.removeIf(acc -> acc.getUsername().equals(username));
        save();
        logger.info("Removed account: {}", username);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public Account getAccount(String username) {
        return accounts.stream()
                .filter(acc -> acc.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void updateAccount(Account account) {
        Account existing = getAccount(account.getUsername());
        if (existing != null) {
            int index = accounts.indexOf(existing);
            accounts.set(index, account);
            save();
            logger.info("Updated account: {}", account.getUsername());
        }
    }

    public boolean validateAccount(Account account) {
        return account.getUsername() != null && !account.getUsername().isEmpty()
                && (account.getAccessToken() != null || account.isOffline());
    }

    public void save() {
        configService.saveAccounts(accounts);
    }
}
