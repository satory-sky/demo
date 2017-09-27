package au.com.serenity.ms.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component 
public class CacheEvicter {
	
	@CacheEvict(value="messageCache", keyGenerator="readServiceAgreementForAccountKeyGenerator")
	public void readServiceAgreementForAccount(String entityId, String channelId, String accountId) {
		// no body. Just evict the cache with this key.
	}
}
