@echo off
setlocal

REM Caminho da pasta com o JAR
set INPUT_DIR=C:\Users\gmpcp2\Downloads\bar-optimization-system\out\artifacts\bar_optimization_system_jar

REM Nome do JAR
set JAR_NAME=bar-optimization-system.jar

REM Nome da classe principal
set MAIN_CLASS=calculator.bar_optimization_system.InterfaceCalculatorApplication

REM Ícone (caminho relativo ou absoluto)
set ICON=%INPUT_DIR%\resources\icon.ico

REM Nome do aplicativo
set APP_NAME=bar_optimization_system

REM Caminho do JavaFX SDK (versão 21 compatível com seu Java)
set JAVAFX_SDK=C:\Users\gmpcp2\Downloads\javafx-sdk-21.0.7

REM Caminho das bibliotecas nativas (pode precisar ser ajustado para sua versão do SDK)
set JAVA_LIB_PATH=%JAVAFX_SDK%\bin

REM Executar jpackage com as opções para o JavaFX
jpackage ^
  --name %APP_NAME% ^
  --input "%INPUT_DIR%" ^
  --main-jar %JAR_NAME% ^
  --main-class %MAIN_CLASS% ^
  --type exe ^
  --icon "%ICON%" ^
  --java-options "--enable-preview -Dprism.order=sw -Dprism.verbose=true -Djava.library.path=%JAVA_LIB_PATH%" ^
  --module-path "%JAVAFX_SDK%\lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  --win-console ^
  --verbose

pause
