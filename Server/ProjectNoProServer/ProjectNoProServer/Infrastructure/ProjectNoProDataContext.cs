using ProjectNoProServer.Entities;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace ProjectNoProServer.Infrastructure
{
    public class ProjectNoProDataContext : DbContext
    {
        public ProjectNoProDataContext() : base("name=projectnoproconnectionstring")
        {
        }
        public DbSet<Message> Messages { get; set; }
    }
}