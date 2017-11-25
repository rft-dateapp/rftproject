using Pub_n_Fun.Cache;
using Pub_n_Fun.Error;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Pub_n_Fun.Models;
using Pub_n_Fun.EDM;

namespace Pub_n_Fun
{
    public class PubnFunCore : IPubnFunCore
    {
        private static List<Models.Pub> Pubs = new List<Models.Pub>();
        private static List<EDM.Pub> awe = new List<EDM.Pub>();

        public void AddOpinion(customerOpinion OpinionToBeAdded)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.customerOpinions.Add(OpinionToBeAdded);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void AddPub(EDM.Pub PubToBeAdded)
        {
            try
            {
                //Pubs.Add(PubToBeAdded);

                using (var db = new EDM.RftKocsmaAppDBEntities())
                {
                    db.Pubs.Add(PubToBeAdded);

                    db.SaveChanges();
                }
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void DeleteOpinionByID(string opinionID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.customerOpinions.Remove(db.customerOpinions.Find(opinionID));
                    db.SaveChanges();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void DeletePubByID(string pubID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.Pubs.Remove(db.Pubs.Find(pubID));
                    db.SaveChanges();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<EDM.customerOpinion> GetAllOpinionListAboutPubByID(string pubID)
        {
            try
            {
                if (int.TryParse(pubID, out int tmp))
                {
                    using (var db = new RftKocsmaAppDBEntities())
                    {
                        return db.customerOpinions.Where(p => p.pubID == tmp).ToList();
                    }
                }
                else
                {
                    return null;
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public List<EDM.Pub> GetAllPubList()
        {
            try
            {
                // not working , does not really acces any data in the db
                using (RftKocsmaAppDBEntities db = new RftKocsmaAppDBEntities())
                {
                    return db.Pubs.SqlQuery("SELECT * FROM dbo.Pubs").ToList();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public customerOpinion GetCustomerOpinion(string opinionID)
        {
            try
            {
                using ( var db = new RftKocsmaAppDBEntities())
                {
                    return db.customerOpinions.Find(opinionID);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public EDM.Pub GetPubByID(string pubID)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    return db.Pubs.Find(pubID);
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public string GetSystemDate(string token)
        {
            try
            {
                if (CacheManager.ValidateToken(token))
                {
                    return DateTime.Now.ToString();
                }
                else
                {
                    throw new InvalidTokenException("Invalid given token at GetSystemDate");
                }
            }
            catch(Exception e)
            {
                throw e;
            }
        }

        public void UpdateOpinion(customerOpinion OpinionToBeUpdated)
        {
            throw new NotImplementedException();
        }

        public void UpdatePub(EDM.Pub PubToBeUpdated)
        {
            throw new NotImplementedException();
        }
    }
}
