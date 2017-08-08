package com.hydt.app.config.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by bean_huang on 2017/7/11.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
