package com.hydt.app.config;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;

/**
 * Created by bean_huang on 2017/7/17.
 */
public class FasionFailureAnalyzer implements FailureAnalyzer {
    /**
     * Returns an analysis of the given {@code failure}, or {@code null} if no analysis
     * was possible.
     *
     * @param failure the failure
     * @return the analysis or {@code null}
     */
    @Override
    public FailureAnalysis analyze(Throwable failure) {
        System.out.println("-----------FasionFailureAnalyzer---------------");
        FailureAnalysis failureAnalysis = new FailureAnalysis("uncath","startup", failure);
        return failureAnalysis;
    }
}
