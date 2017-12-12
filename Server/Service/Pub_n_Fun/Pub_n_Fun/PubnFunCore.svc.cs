using Pub_n_Fun.Cache;
using Pub_n_Fun.Error;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using Pub_n_Fun.Models;
using Pub_n_Fun.EDM.tmp;
using Pub_n_Fun.Manager;

namespace Pub_n_Fun
{
    public class PubnFunCore : IPubnFunCore
    {
        private static List<Models.Pub> Pubs = new List<Models.Pub>();
        private static List<EDM.tmp.Pubs> awe = new List<EDM.tmp.Pubs>()
                        {
                            new EDM.tmp.Pubs()
                            {
                                pubID = 1,
                                address = "Debrecen, Zákány utca 26.",
                                name = "Campus Hotel és Kollégium",
                                customerOpinions = new List<customerOpinions>()
                                {
                                    new customerOpinions()
                                    {
                                            opinion = "nincs meleg viz, szar a net, cuck fampus",
                                            rating = 1,
                                            pubID = 1,
                                    },
                                    new customerOpinions()
                                    {
                                            opinion = "uuuuhhhh pull the trigger! Aint nobody gona do it for you.",
                                            rating = 5,
                                            pubID = 1,
                                    },
                                },
                                latitude = (float)47.545093,
                                longitude = (float)21.640869,
                            },
                            new EDM.tmp.Pubs()
                            {
                                pubID = 2,
                                address = "isten háta mögöttön is túl",
                                name = "kurtafarkú malac kcosmája",
                                latitude = 0,
                                longitude = 0,
                            },
                            new EDM.tmp.Pubs()
                            {
                                pubID = 3,
                                address = "szomszéd",
                                name = "szomszéd",
                                latitude = 100,
                                longitude = 100,
                            },
                        };

        

        public void AddOpinion(customerOpinions OpinionToBeAdded)
        {
            try
            {
                using (var db = new RftKocsmaAppDBEntities())
                {
                    db.customerOpinions.Add(OpinionToBeAdded);

                    db.Pubs.Find(OpinionToBeAdded.pubID).customerOverallRatings = PubManager.CalculateOverAllRatingPub(OpinionToBeAdded.pubID.ToString());

                    db.SaveChanges();
                }
            }
            catch (Exception e)
            {

                throw e;
            }
        }

        public void AddPub(EDM.tmp.Pubs PubToBeAdded)
        {
            try
            {                
                using (var db = new RftKocsmaAppDBEntities())
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

                    db.Pubs.Find(db.customerOpinions.Find(opinionID).pubID).customerOverallRatings = PubManager.CalculateOverAllRatingPub(db.customerOpinions.Find(opinionID).pubID.ToString());
                    
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

        public List<EDM.tmp.customerOpinions> GetAllOpinionListAboutPubByID(string pubID)
        {
            try
            {
                if (int.TryParse(pubID, out int tmp))
                {
                    using (var db = new RftKocsmaAppDBEntities())
                    {
                        return db.customerOpinions.Where(p => p.pubID == tmp).ToList();
                        //eturn db.customerOpinions.SqlQuery("SELECT * FROM dbo.customerOpinions WHERE pubID=" + tmp + ";").ToList();
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

        public List<EDM.tmp.Pubs> GetAllPubList()
        {
            try
            {
                List<Pubs> Pubs = new List<EDM.tmp.Pubs>();

                using (var db = new RftKocsmaAppDBEntities())
                {                  
                    Pubs = db.Pubs.ToList();

                    foreach (EDM.tmp.Pubs pub in Pubs)
                    {
                        pub.customerOverallRatings = PubManager.CalculateOverAllRatingPub(pub.pubID.ToString());
                    }

                    db.SaveChanges();

                    Pubs = db.Pubs.ToList();

                    return Pubs;
                }
            }
            catch (Exception e)
            {
                throw e;
            }
            
        }

        public customerOpinions GetCustomerOpinion(string opinionID)
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

        public EDM.tmp.Pubs GetPubByID(string pubID)
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

        public void UpdateOpinion(customerOpinions OpinionToBeUpdated)
        {
            throw new NotImplementedException();
        }

        public void UpdatePub(EDM.tmp.Pubs PubToBeUpdated)
        {
            throw new NotImplementedException();
        }
    }
}
