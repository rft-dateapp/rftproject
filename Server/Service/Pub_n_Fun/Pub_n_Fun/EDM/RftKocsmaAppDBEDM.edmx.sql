
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, 2012 and Azure
-- --------------------------------------------------
-- Date Created: 12/07/2017 22:27:18
-- Generated from EDMX file: C:\Users\Nealder\Desktop\rft project root\rftproject\Server\Service\Pub_n_Fun\Pub_n_Fun\EDM\RftKocsmaAppDBEDM.edmx
-- --------------------------------------------------

SET QUOTED_IDENTIFIER OFF;
GO
USE [rftpubnfundb];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[FK__customerO__custo__5CD6CB2B]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[customerOpinions] DROP CONSTRAINT [FK__customerO__custo__5CD6CB2B];
GO
IF OBJECT_ID(N'[dbo].[FK__customerO__pubID__4BAC3F29]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[customerOpinions] DROP CONSTRAINT [FK__customerO__pubID__4BAC3F29];
GO

-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[customerOpinions]', 'U') IS NOT NULL
    DROP TABLE [dbo].[customerOpinions];
GO
IF OBJECT_ID(N'[dbo].[customers]', 'U') IS NOT NULL
    DROP TABLE [dbo].[customers];
GO
IF OBJECT_ID(N'[dbo].[Pubs]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Pubs];
GO

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'customerOpinions'
CREATE TABLE [dbo].[customerOpinions] (
    [opinionID] int  NOT NULL,
    [pubID] int  NULL,
    [rating] real  NULL,
    [customerId] int  NOT NULL,
    [opinion] varchar(500)  NULL
);
GO

-- Creating table 'customers'
CREATE TABLE [dbo].[customers] (
    [customerId] int  NOT NULL,
    [customerName] varchar(60)  NOT NULL
);
GO

-- Creating table 'Pubs'
CREATE TABLE [dbo].[Pubs] (
    [pubID] int  NOT NULL,
    [address] varchar(100)  NOT NULL,
    [customerOverallRatings] int  NULL,
    [name] varchar(50)  NOT NULL,
    [longitude] real  NULL,
    [latitude] real  NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [opinionID] in table 'customerOpinions'
ALTER TABLE [dbo].[customerOpinions]
ADD CONSTRAINT [PK_customerOpinions]
    PRIMARY KEY CLUSTERED ([opinionID] ASC);
GO

-- Creating primary key on [customerId] in table 'customers'
ALTER TABLE [dbo].[customers]
ADD CONSTRAINT [PK_customers]
    PRIMARY KEY CLUSTERED ([customerId] ASC);
GO

-- Creating primary key on [pubID] in table 'Pubs'
ALTER TABLE [dbo].[Pubs]
ADD CONSTRAINT [PK_Pubs]
    PRIMARY KEY CLUSTERED ([pubID] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [customerId] in table 'customerOpinions'
ALTER TABLE [dbo].[customerOpinions]
ADD CONSTRAINT [FK__customerO__custo__5CD6CB2B]
    FOREIGN KEY ([customerId])
    REFERENCES [dbo].[customers]
        ([customerId])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK__customerO__custo__5CD6CB2B'
CREATE INDEX [IX_FK__customerO__custo__5CD6CB2B]
ON [dbo].[customerOpinions]
    ([customerId]);
GO

-- Creating foreign key on [pubID] in table 'customerOpinions'
ALTER TABLE [dbo].[customerOpinions]
ADD CONSTRAINT [FK__customerO__pubID__4BAC3F29]
    FOREIGN KEY ([pubID])
    REFERENCES [dbo].[Pubs]
        ([pubID])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK__customerO__pubID__4BAC3F29'
CREATE INDEX [IX_FK__customerO__pubID__4BAC3F29]
ON [dbo].[customerOpinions]
    ([pubID]);
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------