@echo off
setlocal enabledelayedexpansion

REM Nome e valor da variável de ambiente
set "VAR_NAME=CROSSWORDS_API_KEY"
set "VAR_VALUE=CROSSWORDS_API"

REM Adicione a variável de ambiente
setx %VAR_NAME% %VAR_VALUE%

if %errorlevel% == 0 (
  echo Variável de ambiente adicionada com sucesso!
) else (
  echo Erro ao adicionar a variável de ambiente!
)

pause
