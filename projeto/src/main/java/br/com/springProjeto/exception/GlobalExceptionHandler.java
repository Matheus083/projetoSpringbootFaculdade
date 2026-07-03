package br.com.springProjeto.exception;

import br.com.springProjeto.dto.response.ErroRespostaDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. CAPTURA: Recursos Não Encontrados (404)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroRespostaDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        ErroRespostaDTO erro = ErroRespostaDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .erro("Recurso Não Encontrado")
                .mensagem(ex.getMessage())
                .caminho(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 2. CAPTURA: Violações de Regras de Negócio (400)
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroRespostaDTO> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        ErroRespostaDTO erro = ErroRespostaDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Violação de Regra de Negócio")
                .mensagem(ex.getMessage())
                .caminho(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 3. CAPTURA: Erros de validação do Jakarta Validation (@Valid / @NotBlank / @NotNull) (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> handleValidacaoCampos(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errosCampos = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErroRespostaDTO erro = ErroRespostaDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .erro("Erro de Validação de Dados")
                .mensagem("Um ou mais campos enviados contêm dados inválidos.")
                .caminho(request.getRequestURI())
                .validacoes(errosCampos)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 4. CAPTURA: Qualquer outra exceção inesperada e genérica no servidor (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroRespostaDTO> handleExcecaoGenerica(Exception ex, HttpServletRequest request) {
        ErroRespostaDTO erro = ErroRespostaDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .erro("Erro Interno no Servidor")
                .mensagem("Ocorreu uma falha interna inesperada no sistema. Por favor, tente novamente mais tarde.")
                .caminho(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}