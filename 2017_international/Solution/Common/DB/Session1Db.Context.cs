﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Common.DB
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class Session1Db : DbContext
    {
        public Session1Db()
            : base("name=Session1Db")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<Country> Countries { get; set; }
        public virtual DbSet<Office> Offices { get; set; }
        public virtual DbSet<Role> Roles { get; set; }
        public virtual DbSet<UserLog> UserLogs { get; set; }
        public virtual DbSet<User> Users { get; set; }
    }
}
