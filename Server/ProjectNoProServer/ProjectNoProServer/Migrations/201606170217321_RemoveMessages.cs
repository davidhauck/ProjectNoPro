namespace ProjectNoProServer.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class RemoveMessages : DbMigration
    {
        public override void Up()
        {
            DropTable("dbo.Messages");
        }
        
        public override void Down()
        {
            CreateTable(
                "dbo.Messages",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Value = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
    }
}
