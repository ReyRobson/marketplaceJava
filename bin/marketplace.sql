-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 20/06/2023 às 17:16
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `marketplace`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `anuncio`
--

CREATE TABLE `anuncio` (
  `Titulo` varchar(255) DEFAULT NULL,
  `Descricao` varchar(255) DEFAULT NULL,
  `Data` date DEFAULT NULL,
  `Quantidade` int(11) DEFAULT NULL,
  `Categoria` varchar(25) DEFAULT NULL,
  `Entrega` tinyint(1) DEFAULT NULL,
  `Status` tinyint(1) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `fk_Produtos_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `carrinho`
--

CREATE TABLE `carrinho` (
  `fk_Organizacao_Id` int(11) DEFAULT NULL,
  `fk_Anuncio_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `cria`
--

CREATE TABLE `cria` (
  `fk_Empresa_Id` int(11) DEFAULT NULL,
  `fk_Anuncio_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `empresa`
--

CREATE TABLE `empresa` (
  `Nome` varchar(255) DEFAULT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Endereco` varchar(255) DEFAULT NULL,
  `Cidade` varchar(25) DEFAULT NULL,
  `Estado` varchar(10) DEFAULT NULL,
  `CNPJ` int(11) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `CEP` varchar(25) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Senha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `lista_produtos`
--

CREATE TABLE `lista_produtos` (
  `Id` int(11) NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  `fk_Organizacao_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `organizacao`
--

CREATE TABLE `organizacao` (
  `Nome` varchar(255) DEFAULT NULL,
  `Telefone` varchar(20) DEFAULT NULL,
  `Endereco` varchar(255) DEFAULT NULL,
  `Cidade` varchar(25) DEFAULT NULL,
  `Estado` varchar(10) DEFAULT NULL,
  `CNPJ` int(11) DEFAULT NULL,
  `Id` int(11) NOT NULL,
  `CEP` varchar(25) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Senha` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `possui`
--

CREATE TABLE `possui` (
  `fk_Lista_Produtos_Id` int(11) DEFAULT NULL,
  `fk_Anuncio_Id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `produtos`
--

CREATE TABLE `produtos` (
  `Nome` varchar(255) DEFAULT NULL,
  `Subcategoria` varchar(25) DEFAULT NULL,
  `Disponibilidade` tinyint(1) DEFAULT NULL,
  `Caracteristicas` varchar(255) DEFAULT NULL,
  `Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `anuncio`
--
ALTER TABLE `anuncio`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FK_Anuncio_2` (`fk_Produtos_Id`);

--
-- Índices de tabela `carrinho`
--
ALTER TABLE `carrinho`
  ADD KEY `FK_Carrinho_1` (`fk_Organizacao_Id`),
  ADD KEY `FK_Carrinho_2` (`fk_Anuncio_Id`);

--
-- Índices de tabela `cria`
--
ALTER TABLE `cria`
  ADD KEY `FK_Cria_1` (`fk_Empresa_Id`),
  ADD KEY `FK_Cria_2` (`fk_Anuncio_Id`);

--
-- Índices de tabela `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`Id`);

--
-- Índices de tabela `lista_produtos`
--
ALTER TABLE `lista_produtos`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `FK_Lista_Produtos_2` (`fk_Organizacao_Id`);

--
-- Índices de tabela `organizacao`
--
ALTER TABLE `organizacao`
  ADD PRIMARY KEY (`Id`);

--
-- Índices de tabela `possui`
--
ALTER TABLE `possui`
  ADD KEY `FK_Possui_1` (`fk_Lista_Produtos_Id`),
  ADD KEY `FK_Possui_2` (`fk_Anuncio_Id`);

--
-- Índices de tabela `produtos`
--
ALTER TABLE `produtos`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `anuncio`
--
ALTER TABLE `anuncio`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `empresa`
--
ALTER TABLE `empresa`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `lista_produtos`
--
ALTER TABLE `lista_produtos`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `organizacao`
--
ALTER TABLE `organizacao`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `produtos`
--
ALTER TABLE `produtos`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `anuncio`
--
ALTER TABLE `anuncio`
  ADD CONSTRAINT `FK_Anuncio_2` FOREIGN KEY (`fk_Produtos_Id`) REFERENCES `produtos` (`Id`);

--
-- Restrições para tabelas `carrinho`
--
ALTER TABLE `carrinho`
  ADD CONSTRAINT `FK_Carrinho_1` FOREIGN KEY (`fk_Organizacao_Id`) REFERENCES `organizacao` (`Id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_Carrinho_2` FOREIGN KEY (`fk_Anuncio_Id`) REFERENCES `anuncio` (`Id`);

--
-- Restrições para tabelas `cria`
--
ALTER TABLE `cria`
  ADD CONSTRAINT `FK_Cria_1` FOREIGN KEY (`fk_Empresa_Id`) REFERENCES `empresa` (`Id`),
  ADD CONSTRAINT `FK_Cria_2` FOREIGN KEY (`fk_Anuncio_Id`) REFERENCES `anuncio` (`Id`) ON DELETE SET NULL;

--
-- Restrições para tabelas `lista_produtos`
--
ALTER TABLE `lista_produtos`
  ADD CONSTRAINT `FK_Lista_Produtos_2` FOREIGN KEY (`fk_Organizacao_Id`) REFERENCES `organizacao` (`Id`) ON DELETE CASCADE;

--
-- Restrições para tabelas `possui`
--
ALTER TABLE `possui`
  ADD CONSTRAINT `FK_Possui_1` FOREIGN KEY (`fk_Lista_Produtos_Id`) REFERENCES `lista_produtos` (`Id`) ON DELETE SET NULL,
  ADD CONSTRAINT `FK_Possui_2` FOREIGN KEY (`fk_Anuncio_Id`) REFERENCES `anuncio` (`Id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
