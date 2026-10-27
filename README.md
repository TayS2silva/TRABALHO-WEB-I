# Patinhas — adoção de animais

Projeto web com Java 17, Spring Boot, Thymeleaf, HTML e CSS. Não usa banco de dados: os dados ficam na memória e voltam aos exemplos iniciais quando o servidor é reiniciado.

## Requisitos
- JDK 17 ou superior
- Maven 3.8+ (ou usar o Maven integrado do NetBeans)
- Internet na primeira compilação para baixar as dependências

## Executar
1. Extraia a pasta `patinhas-spring-boot`.
2. Abra a pasta como projeto Maven no NetBeans (File > Open Project).
3. Execute `PatinhasApplication.java`, ou no terminal dentro da pasta rode:
   - Windows: `mvnw.cmd spring-boot:run` (se houver Maven Wrapper) ou `mvn spring-boot:run`
   - Linux/macOS: `mvn spring-boot:run`
4. Acesse `http://localhost:8080`.

## Funcionalidades
- Página inicial com cartões de animais.
- Busca por nome/descrição e filtros por espécie e situação.
- Cadastro, edição e exclusão de animais.
- Envio e acompanhamento de solicitações de adoção.
- Painel administrativo com resumo dos animais e solicitações.

## Observações importantes
- Como não há banco de dados, os dados são temporários.
- Para fotos, informe uma URL de imagem no formulário ou coloque imagens em `src/main/resources/static/images/` e use caminhos como `/images/bella.jpg`.
