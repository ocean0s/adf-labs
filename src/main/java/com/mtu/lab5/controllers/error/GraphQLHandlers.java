package com.mtu.lab5.controllers.error;

import com.mtu.lab5.services.exceptions.NotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQLHandlers extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        switch (ex) {
            case NotFoundException notFoundException -> {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.NOT_FOUND)
                        .message(ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            }
            case DataIntegrityViolationException dataIntegrityViolationException -> {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.INTERNAL_ERROR)
                        .message("Could not execute the command as it would have caused an integrity issue.")
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build();
            }
            case ConstraintViolationException constraintViolationException -> {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.BAD_REQUEST)
                        .message(ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build();
            }
            default -> { // Unhandled exception
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.INTERNAL_ERROR)
                        .message("Unknown error occurred.\n" + ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            }
        }
    }
}
