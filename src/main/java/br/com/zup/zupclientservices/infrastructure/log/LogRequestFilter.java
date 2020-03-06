package br.com.zup.zupclientservices.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import java.io.IOException;

@Component
public class LogRequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StopWatch watch = new StopWatch();
        watch.start();

        filterChain.doFilter(servletRequest, servletResponse);

        watch.stop();

        this.logRequest(watch, servletRequest, servletResponse);
    }

    private void logRequest(StopWatch watch, ServletRequest servletRequest, ServletResponse servletResponse) {
        logger.info(new LogBuilder().withRequest(servletRequest).withResponse(servletResponse).withTime(watch).build());
    }

}
