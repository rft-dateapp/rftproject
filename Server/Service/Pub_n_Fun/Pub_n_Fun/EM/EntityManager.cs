using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;
using Pub_n_Fun.Models;

namespace Pub_n_Fun.EM
{
    public class EntityManager : DbContext
    {
        private DbSet<CustomerOpinion> customerOpinionsEM;
        private DbSet<Pub> pubs;

        public DbSet<CustomerOpinion> CustomerOpinionsEM
        {
            get => customerOpinionsEM;

            set => customerOpinionsEM = value;
        }

        public DbSet<Pub> Pubs
        {
            get => pubs;

            set => pubs = value;
        }
    }
}